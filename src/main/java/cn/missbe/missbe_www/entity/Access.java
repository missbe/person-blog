package cn.missbe.missbe_www.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author liaoxing
 * @date 16/8/16 19:21
 */
@Entity
@Table(name = "access_mgt")
public class Access {

    @Id
    @GeneratedValue
    private long id;
    private String ip;
    private Date time = new Date();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
