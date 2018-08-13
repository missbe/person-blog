package cn.missbe.missbe_www.entity.blog;

import cn.missbe.missbe_www.entity.DataTableInterface;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author liaoxing
 * @date 16/9/9 15:08
 */
@Entity
@Table(name = "section_mgt")
public class Section implements DataTableInterface {
    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "栏目名称必填")
    private String description;
    private String authorAccount;
    @NotEmpty(message = "必须选择所属菜单.")
    private String menu;
    private String menuName;
    private boolean indexShow;
    @Length(min = 2, max = 80, message = "栏目简介必须在2到80字符之间.")
    @NotEmpty(message = "必须填写栏目简介.")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Override
    public String[] dataTableTrs() {
        return new String[]{"栏目ID", "栏目名字", "栏目简介", "所属菜单", "首页展示"};
    }

    @Override
    public String[] toDataTableArray() {
        return new String[]{this.getId() + "", this.getDescription(), this.getContent(), this.getMenuName(), this.indexShow ? "首页展示栏目" : "否"};
    }

    @Override
    public String[] indexVarNames() {
        return new String[]{"id", "description", "content", "menuName", "indexShow"};
    }

    @Override
    public String searchVar() {
        return "description";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        return id == section.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public boolean isIndexShow() {
        return indexShow;
    }

    public void setIndexShow(boolean indexShow) {
        this.indexShow = indexShow;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
