package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.JsonKeyValue;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author lyg
 * @date 16/8/15 20:16
 */
public interface JsonKeyValueService {
    JsonBaseResult save(JsonKeyValue jsonKeyValue);

    JSONObject findByIdForce(String key);

    JSONObject findById(String key);

    List<JsonKeyValue> findAll();
}
