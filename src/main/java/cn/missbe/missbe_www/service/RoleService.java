package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.dto.PaginationResult;
import cn.missbe.missbe_www.entity.Permission;
import cn.missbe.missbe_www.entity.Role;
import cn.missbe.missbe_www.form.DataTableForm;

import java.util.List;

public interface RoleService {


    void update(Role role);

    PaginationResult paginateSearch(DataTableForm dataTableForm);

    JsonBaseResult save(String id, String name, String content,String[] permissions);

    List<Role> findAll();

    Role findById(String id);

    void deleteById(String id);

    JsonBaseResult deleteByIds(List<String> ids);


}
