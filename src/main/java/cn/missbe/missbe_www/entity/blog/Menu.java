package cn.missbe.missbe_www.entity.blog;

import cn.missbe.missbe_www.entity.DataTableInterface;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lyg
 * @date 16/9/10 01:19
 */
@Entity
@Table(name = "menu_mgt")
public class Menu implements DataTableInterface {
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "菜单名字必填.")
    @Length(min = 2, max = 10, message = "菜单名字必须在2到10字符之间.")
    private String name;
    @NotEmpty(message = "菜单英文必填.")
    @Length(min = 2, max = 10, message = "菜单英文必须在2到10字符之间.")
    private String englishName;
    private String authorAccount;
    @Length(min = 2, max = 80, message = "菜单简介必须在2到80字符之间.")
    @NotEmpty(message = "必须填写菜单简介.")
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // jquery DataTable 列表头中文
    public String[] dataTableTrs() {
        return new String[]{"ID", "菜单名字", "简介", "菜单英文"};
    }

    public final String[] toDataTableArray() {
        return new String[]{this.getId() + "", this.getName(), this.getContent(), this.getEnglishName()};
    }

    public String[] indexVarNames() {
        return new String[]{"id", "name", "content", "englishName"};
    }

    public String searchVar() {
        return "name";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return id == menu.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
