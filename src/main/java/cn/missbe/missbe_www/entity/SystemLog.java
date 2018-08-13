package cn.missbe.missbe_www.entity;

import cn.missbe.missbe_www.service.SystemLogService;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author liaoxing
 * @date 2016年7月25日 下午3:27:08
 */
@Entity
@Table(name = "system_log_mgt")
public class SystemLog implements Serializable {

    private static final long serialVersionUID = -6854423671693792099L;

    /**
     * 保存一个静态的service，方便直接使用
     */
    public static SystemLogService service;

    @Id
    @GeneratedValue
    private long id;
    private Date time = new Date();
    private String level;
    private String username;
    private String url;
    @Column(columnDefinition = "text")
    private String msg;
    @Column(columnDefinition = "text")
    private String params;
    private String ip;

    /**
     * 日志级别枚举<br>
     * debug Level : 指出细粒度信息事件对调试应用程序是非常有帮助的。<br>
     * info level : 表明消息在粗粒度级别上突出强调应用程序的运行过程。<br>
     * warning level : 表明会出现潜在错误的情形。<br>
     * error level : 指出发生错误事件。
     *
     * @author liaoxing
     * @date 2016年6月30日 下午4:15:40
     */
    public enum Level {
        debug, info, warning, error;

        public static boolean AmoreB(Level A, Level B) {
            return A.equals(error) || toInt(A) >= toInt(B);
        }

        public static Level strToLevel(String levelStr) {
            if (StringUtils.isBlank(levelStr)) {
                return debug;
            }
            levelStr = levelStr.toLowerCase();
            switch (levelStr) {
                case "debug":
                    return debug;
                case "info":
                    return info;
                case "warning":
                    return warning;
                case "error":
                    return error;
                default:
                    return debug;
            }
        }

        private static int toInt(Level level) {
            switch (level) {
                case debug:
                    return 0;
                case info:
                    return 1;
                case warning:
                    return 2;
                case error:
                    return 3;
                default:
                    return 0;
            }
        }
    }


    public Date getTime() {
        return time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
