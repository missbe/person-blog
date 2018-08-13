package cn.missbe.missbe_www.entity;

import com.alibaba.fastjson.annotation.JSONType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * 角色实体
 *
 * @author missbe
 * @date 2016年7月26日 下午10:29:23
 */
@Entity
@Table(name = "role_mgt")
@JSONType(ignores = {"users"})
public class Role implements Serializable,DataTableInterface{

    private static final long serialVersionUID = 6177417450707400228L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    // 角色名称
    @Column(length = 50)
    private String name;

    // 角色描述
    @Column(length = 50)
    private String description;

    // 拥有的权限
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission_mgt", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "pms_id", referencedColumnName = "pms_id")})
    private Collection<Permission> pmss;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "roles")
    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Permission> getPmss() {
        return pmss;
    }

    public void setPmss(Collection<Permission> pmss) {
        this.pmss = pmss;
    }

    @Override
    public String[] dataTableTrs() {
        return new String[]{"ID","角色名称","角色描述","权限路径"};
    }

    @Override
    public String[] toDataTableArray() {
        Permission[] permissions=this.getPmss().toArray(new Permission[0]);
        StringBuffer buffer=new StringBuffer();
        String temp="-无权限-";
        for (int i=0;i<permissions.length;i++){
            buffer.append(permissions[i].getName()+"-/-");
        }//end for
        if(buffer.length()>1){
            temp=buffer.substring(0,buffer.length()-2);
        }else{
            temp=buffer.toString();
        }
        return new String[]{""+this.getId(),this.getName(),this.getDescription(),temp};
    }

    @Override
    public String[] indexVarNames() {
        return new String[]{"id","name","description","users"};
    }

    @Override
    public String searchVar() {
        return "name";
    }
}