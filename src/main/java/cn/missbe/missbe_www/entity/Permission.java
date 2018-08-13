package cn.missbe.missbe_www.entity;

import com.alibaba.fastjson.annotation.JSONType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 权限实体
 *
 * @author liaoxing
 * @date 2016年7月26日 下午10:13:56
 */
@Entity
@Table(name = "permission_mgt")
@JSONType(ignores = {"roles"})
public class Permission implements Serializable,DataTableInterface {

    private static final long serialVersionUID = -8792590494605747957L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pms_id")
    private Long id;

    // 权限名称
    @Column(length = 50)
    private String name;

    // 权限地址
    @Column(length = 50)
    private String permission;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "pmss")
    private Collection<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String[] dataTableTrs() {
        return new String[]{"编号","权限名称","URL路径"};
    }

    @Override
    public String[] toDataTableArray() {
        return new String[]{""+this.getId(),this.getName(),this.getPermission()};
    }

    @Override
    public String[] indexVarNames() {
        return new String[]{"id","name","permission"};
    }

    @Override
    public String searchVar() {
        return "name";
    }
}
