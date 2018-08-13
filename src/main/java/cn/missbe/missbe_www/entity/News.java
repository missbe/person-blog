package cn.missbe.missbe_www.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 新闻实体(公司动态)
 * @author liaoxing
 * @date 16/8/8 15:17
 */
@Entity
@Table(name = "news_mgt")
public class News {
    @Id
    @GeneratedValue
    private long id;
    @Length(min = 5, max = 48, message = "新闻标题必须在5到48字符之间.")
    @NotEmpty(message = "新闻标题必填.")
    private String title;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "新闻内容必填.")
    @Length(min = 30, max = 10000, message = "新闻内容必须在30到10000字符之间.")
    private String content;
    @NotEmpty(message = "作者不能为空.")
    private String author;
    @NotEmpty(message = "新闻类型不能为空.")
    private String type;
    private Date createDate = new Date();
    private boolean top;
    private long click;

    public long getId() {
        return id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public long getClick() {
        return click;
    }

    public void setClick(long click) {
        this.click = click;
    }
}
