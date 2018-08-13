package cn.missbe.missbe_www.entity;

import cn.missbe.missbe_www.util.MD5Util;
import cn.missbe.missbe_www.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户类实体
 *
 * @author Missbe
 * @date 2016年7月26日 下午10:08:28
 */
@Entity
@Table(name = "user_mgt")
@JSONType(ignores = {"password"})
public class User implements Serializable,DataTableInterface {

    private static final long serialVersionUID = 7419229779731522702L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 用户名
    @Column(nullable = false, length = 50)
    private String account;

    // 密码
    @Column(length = 50)
    @NotEmpty(message = "密码必填.")
    private String password;

    // 昵称
    @Column(length = 50)
    @Length(min = 2, max = 16, message = "昵称必须在2到16字符之间.")
    @NotEmpty(message = "昵称必填.")
    private String nickname;

    private Date lastLoginTime;

    // 状态(0、正常，1、锁定)
    private int status;

    // 头像
    @NotEmpty(message = "头像必须上传.")
    private String avator;

    // 拥有的角色
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_mgt", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private Collection<Role> roles;

    @Override
    public String[] dataTableTrs() {

        return new String[]{"ID","用户名","用户状态(0正常 1锁定)","上次登录时间","拥有角色"};
    }

    @Override
    public String[] toDataTableArray() {
        Role[] roles=this.getRoles().toArray(new Role[0]);
        StringBuffer buffer=new StringBuffer();
        String temp="--无角色--";
        for (int i=0;i<roles.length;i++){
            buffer.append(roles[i].getName()+"-/-");
        }
        if(buffer.length()>1){
            temp=buffer.substring(0,buffer.length()-2);
        }else{
            temp=buffer.toString();
        }
        return new String[]{""+this.getId(),this.getAccount(),""+this.getStatus(),this.getLastLoginTime().toString(),temp};
    }

    @Override
    public String[] indexVarNames() {
        return new String[]{"id","account","status","lastLoginTime","roles"};
    }

    @Override
    public String searchVar() {
        return "account";
    }

    public User() {
        this.avator = "static/images/logo.jpg";
    }

    public User(User user) {
        this.id = user.getId();
        this.account = user.getAccount();
        this.nickname = user.getNickname();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5Util.string2MD5(password);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    public String toString() {
        return account;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAvator() {
        if (StringUtils.isBlank(avator)) {
            return "static/images/logo.jpg";
        }
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    /**
     * 用户帐户正常返回true,锁定返回false
     * @return
     */
    public boolean isRecommend() {
        return status==0 ? true : false;
    }
}