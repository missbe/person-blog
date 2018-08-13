package cn.missbe.missbe_www.entity.comment;

import cn.missbe.missbe_www.entity.DataTableInterface;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.entity.blog.Blog;
import cn.missbe.missbe_www.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.*;

@Entity
@Table(name = "themes_missbe")
public class Themes implements DataTableInterface{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "theme_id")
	private int id;

	@Transient
	private String idStr;

	@Column(name = "status")
	private  boolean status;

		@Column(name = "avator")
	private String avator;

	@Column(name = "publisher")
	private String publisher;

	@Basic
	@Column(name = "content",columnDefinition = "text")
	@NotEmpty(message = "主题内容必填.")
	@Length(min = 4, max = 1000, message = "主题内容必填必须在4到800字符之间.")
	private String content;

	@Basic
	@Column(name = "time")
	private String time;

	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "themes_comments_mgt", joinColumns = {
			@JoinColumn(name = "themes_comments_id", referencedColumnName = "theme_id")}, inverseJoinColumns = {
			@JoinColumn(name = "comments_themes_id", referencedColumnName = "comment_id")})
	private List<Comment> comments=new ArrayList<>();

	@ManyToOne(targetEntity = User.class )
	@JoinColumn(name = "author" )
	private User user;

	@ManyToOne(targetEntity = Blog.class)
	@JoinColumn(name = "blog_id" )
	private Blog blog;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getIdStr() {
		return String.valueOf(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	@Override
	public String[] dataTableTrs() {
		return new String[]{"ID","用户","内容","发表时间","审核状态"};
	}

	@Override
	public String[] toDataTableArray() {
		return new String[]{""+this.getId(),this.getPublisher(),
				this.getContent().length()>30 ? this.getContent().substring(0,30) : this.getContent(),
				this.getTime(),this.isStatus()==true ? "已通过":"未通过"};
	}

	@Override
	public String[] indexVarNames() {
		return new String[]{"id","publisher","content","time","status"};
	}

	@Override
	public String searchVar() {
		return "publisher";
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
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


	public String getPublisher() {
		if (StringUtils.isBlank(publisher)) {
			return "匿名";
		}
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
