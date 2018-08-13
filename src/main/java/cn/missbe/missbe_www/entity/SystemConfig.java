package cn.missbe.missbe_www.entity;

import cn.missbe.missbe_www.service.SystemConfigService;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 系统配置实体
 *
 * @author lyg
 * @date 2016年7月25日 下午4:03:48
 */
@Entity
@Table(name = "system_config_mgt")
public class SystemConfig implements Serializable {

    private static final long serialVersionUID = -9120008378749815050L;

    /**
     * 保存一个静态的service，方便直接使用
     */
    public static SystemConfigService service;

    @Id
    // key值
    private String id;
    // 对应属性值
    private String value = "";
    // 对应属性中文含义(application配置)
    private String text = "";
    // 是否application配置
    private boolean important;

    public SystemConfig() {
        super();
    }

    /**
     * @param id        key值
     * @param value     对应属性值
     * @param text      对应属性中文含义(application配置)
     * @param important 是否application配置(关键性配置)
     */
    public SystemConfig(String id, String value, String text, boolean important) {
        super();
        this.id = id;
        this.value = value;
        this.text = text;
        this.important = important;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SystemConfig other = (SystemConfig) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
