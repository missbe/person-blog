package cn.missbe.missbe_www.entity.blog;

import cn.missbe.missbe_www.entity.DataTableInterface;
import cn.missbe.missbe_www.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * @author lyg
 * @date 17/10/12 14:56
 */
@Entity
@Table(name = "blog_mgt")
public class Blog implements DataTableInterface {
    @Id
    @GeneratedValue
    private long id;
    // 标题
    @NotEmpty(message = "文章标题必填.")
    @Length(min = 2, max = 40, message = "文章标题必须在2到40字符之间.")
    private String title;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "文章内容必填.")
    @Length(min = 30, message = "文章内容必须在大于30字符.")
    // 内容
    private String content;
    // 发布时间
    private String date;
    // 点击数
    private long click;
    // 作者姓名
    private String author;
    // 所属栏目
    @NotEmpty(message = "必须选择所属栏目.")
    private String section;
    private String sectionName;
    // 作者账户
    private String authorAccount;
    // 所属一级菜单的menu
    @NotEmpty(message = "必须选择所属菜单.")
    private String menu;
    private String menuName;
    // 图片
    @NotEmpty(message = "无图无真相，必须要有图哦.")
    private String image;
    // 是否推荐
    private boolean recommend;
    // 简易摘要
    @Transient
    private String simpleContent;

    public String getAuthorAccount() {
        return authorAccount;
    }

    public void setAuthorAccount(String authorAccount) {
        this.authorAccount = authorAccount;
    }

    public long getId() {
        return id;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
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

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public String[] dataTableTrs() {
        return new String[]{"ID", "标题", "栏目", "阅读次数", "发布时间", "是否推荐"};
    }

    @Override
    public String[] toDataTableArray() {
        return new String[]{this.getId() + "", this.getTitle(), this.getSectionName(), this.getClick() + "", this.getDate(), this.isRecommend() ? "已推荐" : "否"};
    }

    @Override
    public String[] indexVarNames() {
        return new String[]{"id", "title", "sectionName", "click", "date", "recommend"};
    }

    @Override
    public String searchVar() {
        return "title";
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSimpleContent() {
        String noTag = StringUtils.removeHtmlTag(this.getContent());
        return noTag.length() > 240 ? noTag.substring(0, 140) + "..." : noTag;
    }

    public void setSimpleContent(String simpleContent) {
        this.simpleContent = simpleContent;
    }
}
