package cn.missbe.missbe_www.entity;

import cn.missbe.missbe_www.util.DateUtil;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 客户留言实体(联系我们)
 *
 * @author liaoxing
 * @date 16/8/14 23:44
 */
@Entity
@Table(name = "message_mgt")
public class Message {

    @Id
    @GeneratedValue
    private long id;
    @NotEmpty(message = "姓名必填.")
    @Length(min = 2, max = 10, message = "姓名必须在2到10字符之间.")
    private String name;
    @NotEmpty(message = "联系方式必填.")
    @Length(min = 2, max = 30, message = "联系方式必须在2到30字符之间.")
    private String contact;
    @Column(columnDefinition = "text")
    @NotEmpty(message = "留言内容必填.")
    @Length(min = 5, max = 500, message = "留言内容必须在5到500字符之间.")
    private String message;
    //留言 IP
    private String ip;
    //留言 时间
    private String date = DateUtil.formateDateyyyy_MM_ddHHmmss(new Date());
    // 是否处理的标记
    private boolean handle;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isHandle() {
        return handle;
    }

    public void setHandle(boolean handle) {
        this.handle = handle;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
