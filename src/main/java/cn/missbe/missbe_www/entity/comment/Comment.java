package cn.missbe.missbe_www.entity.comment;

import cn.missbe.missbe_www.entity.DataTableInterface;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_missbe")
public class Comment implements DataTableInterface{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int id;

	@Column(name = "status")
	private  boolean status;

	@ManyToMany(targetEntity = Themes.class,cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "themes_comments_mgt", joinColumns = {
			@JoinColumn(name = "comments_themes_id", referencedColumnName = "comment_id")}, inverseJoinColumns = {
			@JoinColumn(name = "themes_comments_id", referencedColumnName = "theme_id")})
	@JoinColumn(name = "")
	private List<Themes> theme=new ArrayList<>();


	@ManyToOne(targetEntity = User.class,cascade = CascadeType.ALL)
	@JoinColumn(name = "author" )
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "avator")
	private String avator;

	@Column(name = "publisher")
	private String publisher;

	@Basic
	@Column(name = "content")
	@NotEmpty(message = "评论内容必填.")
	@Length(min = 4, max = 1000, message = "评论内容必填必须在4到800字符之间.")
	private String content;

	@Basic
	@Column(name = "time")
	private String time;

	public List<Themes> getTheme() {
		return theme;
	}

	public void setTheme(List<Themes> theme) {
		this.theme = theme;
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

	@Override
    public boolean equals(Object obj){
		if(obj == null){
		           return false;
		}else {
			if(this.getClass() == obj.getClass()){
				Comment o = (Comment) obj;
				if(this.getContent().equals(o.getContent()) && this.getTime().equals(o.getTime())){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
}
