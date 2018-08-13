package cn.missbe.missbe_www.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 保存key value 值, value 是json串,主要存储首页的一些文字信息
 *
 * @author liaoxing
 * @date 16/8/15 00:08
 */
@Entity
@Table(name = "json_key_value_mgt")
public class JsonKeyValue {

    @Id
    @Column(name = "key_")
    @NotNull(message = "JsonKeyValue Key 不能为空")
    private String key;
    @Column(columnDefinition = "text")
    @NotNull(message = "JsonKeyValue 值不能为空")
    @Length(min = 2, max = 3000, message = "value 2-3000 长度")
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSONObject jsonObj() {
        return JSON.parseObject(value);
    }
}
