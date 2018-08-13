package cn.missbe.missbe_www.dao.impl;

import cn.missbe.missbe_www.dao.BaseDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.DataTableInterface;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.form.DataTableForm;
import cn.missbe.missbe_www.util.PrintUtil;
import cn.missbe.missbe_www.util.StringUtils;
import cn.missbe.missbe_www.util.TypeUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @param <T>
 * @author lx 基本操作方法接口实现
 */
@Repository
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {

    // 参数验证,hibernate validator
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private SessionFactory sessionFactory;
    /**
     * 实体类的class
     */
    private Class<T> clazz;

    /**

     * 实体类的主键
     */
    private Class<?> pkClazz;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        Type genType = getClass().getGenericSuperclass();
        if (genType instanceof ParameterizedType) {
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            setClazz((Class) params[0]);
        } else {
            if (!(getClass().getSimpleName().equals(BaseDaoImpl.class.getSimpleName()))) {
                PrintUtil.print("初始化BaseDaoImpl的clazz类型失败:" + getClass().getSimpleName());
            }
        }

    }

    public JsonBaseResult save(T t) {
        List<String[]> errors = null;
        Set<ConstraintViolation<T>> valiRes = null;
        try {
            valiRes = validator.validate(t);
        } catch (Exception e) {
            PrintUtil.print(e.getMessage(), Level.error);
        } finally {
            if (valiRes != null) {
                for (ConstraintViolation<T> i : valiRes) {
                    String[] err = {i.getPropertyPath().toString(), i.getMessage()};
                    if (errors == null) {
                        errors = new ArrayList<>();
                    }
                    errors.add(err);
                }
            }
        }
        if (errors != null) {
            getSession().clear();
            return new JsonBaseResult(errors, false);
        }
        getSession().save(t);
        return new JsonBaseResult("保存成功.", true);
    }

    public void delete(T t) {
        getSession().delete(t);
    }

    public void deleteById(String id) {
        getSession().createQuery("delete from " + clazz.getSimpleName() + " o where o.id=:id ")
                .setParameter("id", getIdObject(id)).executeUpdate();
    }

    public void deleteByHql(String hql, Map<String, Object> param) {
        hql = StringUtils.isNotBlank(hql) ? (hql.toLowerCase().trim().startsWith("o.") ? " WHERE 1=1 AND " : " WHERE 1=1 ") + hql : "";
        hql = "delete from " + this.clazz.getSimpleName() + " o" + hql;
        Query q = getSession().createQuery(hql);
        fillParams(q, param).executeUpdate();
    }

    public void deleteByIds(String[] ids) {
        for (int i = 0; (ids != null) && (i < ids.length); i++) {
            deleteById(ids[i]);
        }
    }

    public JsonBaseResult update(T t) {
        List<String[]> errors = null;
        Set<ConstraintViolation<T>> valiRes = null;
        try {
            valiRes = validator.validate(t);
        } catch (Exception e) {
            PrintUtil.print(e.getMessage(), Level.error);
        } finally {
            if (valiRes != null) {
                for (ConstraintViolation<T> i : valiRes) {
                    String[] err = {i.getPropertyPath().toString(), i.getMessage()};
                    if (errors == null) {
                        errors = new ArrayList<>();
                    }
                    errors.add(err);
                }
            }
        }
        if (errors != null) {
            getSession().clear();
            PrintUtil.print("Error:"+errors,Level.error);
            return new JsonBaseResult(errors, false);
        }
        getSession().update(t);
        return new JsonBaseResult("修改成功.", true);
    }

    public T findById(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        return getSession().get(this.clazz, getIdObject(id));
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return getSession().createQuery("from " + this.clazz.getSimpleName()).list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findListByHql(String hql, Map<String, Object> param) {
        hql = StringUtils.isNotBlank(hql) ? (hql.toLowerCase().trim().startsWith("o.") ? " WHERE 1=1 AND " : " WHERE 1=1 ") + hql : "";
        hql = "from " + this.clazz.getSimpleName() + " o" + hql;
        Query q = getSession().createQuery(hql);
        fillParams(q, param);
        return q.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> findList(int page, int pageSize, String hql, Map<String, Object> param) {
        hql = StringUtils.isNotBlank(hql) ? (hql.toLowerCase().trim().startsWith("o.") ? " WHERE 1=1 AND " : " WHERE 1=1 ") + hql : "";
        hql = "from " + this.clazz.getSimpleName() + " o" + hql;
        Query q = getSession().createQuery(hql);
        fillParams(q, param);
        q.setFirstResult((page - 1) * pageSize);
        q.setMaxResults(pageSize);
        return q.list();
    }

    public List<T> findListByIds(String[] ids) {
        List<T> list = new ArrayList<>();
        for (int i = 0; (ids != null) && (i < ids.length); i++) {
            list.add(findById(ids[i]));
        }
        return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T uniqueResultByHql(String hql, Map<String, Object> param) {
        hql = StringUtils.isNotBlank(hql) ? (hql.toLowerCase().trim().startsWith("o.") ? " WHERE 1=1 AND " : " WHERE 1=1 ") + hql : "";
        hql = "from " + clazz.getSimpleName() + " o" + hql;
        return (T) fillParams(getSession().createQuery(hql), param).uniqueResult();
    }

    @Override
    public long count(String hql, Map<String, Object> param) {
        hql = StringUtils.isNotBlank(hql) ? (hql.toLowerCase().trim().startsWith("o.") ? " WHERE 1=1 AND " : " WHERE 1=1 ") + hql : "";
        hql = "select count(*) from " + clazz.getSimpleName() + " o" + hql;
        return (long) fillParams(getSession().createQuery(hql), param).uniqueResult();
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    private Serializable getIdObject(String id) {
        Serializable idObj;
        try {
            if (TypeUtils.isIntClass(this.pkClazz))
                idObj = Integer.valueOf(id);
            else if (TypeUtils.isLongClass(this.pkClazz))
                idObj = Long.valueOf(id);
            else
                idObj = id;
        } catch (Exception e) {
            e.printStackTrace();
            PrintUtil.print("获取 " + this.clazz.getName() + " 类型失败！", Level.error);
            idObj = id;
        }
        return idObj;
    }

    @SuppressWarnings("rawtypes")
    private Query fillParams(Query q, Map<String, Object> param) {
        if (param == null)
            return q;
        for (String key : param.keySet()) {
            Object parm = param.get(key);
            if (parm instanceof List) {
                q.setParameterList(key, (List) parm);
            } else if (parm instanceof Date) {
                q.setDate(key, (Date) parm);
            } else {
                q.setParameter(key, parm);
            }
        }
        return q;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
        this.pkClazz = TypeUtils.getPkClass(clazz);
    }

    @Override
    public void updateList(List<T> list) {
        if (list == null || list.size() <= 0)
            return;
        for (T t : list) {
            update(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findListMapByHql(String hql, int page, int pageSize) {
        Query q = getSession().createQuery(hql);
        q.setFirstResult((page - 1) * pageSize);
        q.setMaxResults(pageSize);
        return (List<Map<String, Object>>) q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findListMapByHql(String hql) {
        Query q = getSession().createQuery(hql);
        return (List<Map<String, Object>>) q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public void saveList(Collection<T> collection) {
        for (T t : collection) {
            this.save(t);
        }
    }

    @Override
    public PaginationResult<T> paginateSearch(DataTableForm dataTableForm) {
        String hql = "";
        Map<String, Object> params = dataTableForm.getParams();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                hql += " and o." + key + "=:" + key;
            }
        }
        try {
            T ent = clazz.newInstance();
            if (!(ent instanceof DataTableInterface)) {
                return new PaginationResult<>(0, 0, null);
            } else {
                DataTableInterface entDi = (DataTableInterface) ent;
                if (StringUtils.isNotBlank(dataTableForm.getSearchValue())) {
                    hql += " and o." + entDi.searchVar() + " like '%" + dataTableForm.getSearchValue() + "%'";
                }
                hql += " order by o." + entDi.indexVarNames()[dataTableForm.getOrderColumn()] + " " + dataTableForm.getOrderType();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        List<T> result = this.findList(dataTableForm.getPageNow(), dataTableForm.getLength(), hql, params);
        List<T> resultList = new ArrayList<>();
        for (T t : result) {
            resultList.add(t);
        }
        long count = this.count(hql, params);
        return new PaginationResult<>(count, dataTableForm.getDraw(), resultList);
    }

    public Class<T> getClazz() {
        return clazz;
    }

}