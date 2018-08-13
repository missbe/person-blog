package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.App;
import cn.missbe.missbe_www.dao.JsonKeyValueDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.JsonKeyValue;
import cn.missbe.missbe_www.entity.User;
import cn.missbe.missbe_www.service.JsonKeyValueService;
import cn.missbe.missbe_www.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lyg
 * @date 16/8/15 20:16
 */
@Service
public class JsonKeyValueServiceImpl implements JsonKeyValueService {
    @Resource
    private JsonKeyValueDao jsonKeyValueDao;
    @Resource
    private UserService userService;

    @Override
    public JsonBaseResult save(JsonKeyValue jsonKeyValue) {
        JsonKeyValue oldTemp = jsonKeyValueDao.findById(jsonKeyValue.getKey());
        if (oldTemp != null) {
            oldTemp.setValue(jsonKeyValue.getValue());
            return jsonKeyValueDao.update(oldTemp);
        } else {
            return jsonKeyValueDao.save(jsonKeyValue);
        }
    }

    @Override
    public JSONObject findByIdForce(String key) {
        JsonKeyValue jk = jsonKeyValueDao.findById(key);
        if (jk == null) {
            jk = new JsonKeyValue();
            jk.setKey(key);
            if (key.startsWith(App.BLOGJSON_PREFIX)) {
                JSONObject obj = new JSONObject();
                obj.put("weixin", "Missbe");
                obj.put("logo", "static/images/logo.jpg");
                obj.put("photo", "static/images/photo.jpg");
                obj.put("weixinQrcode", "static/images/weixin.jpg");
                obj.put("bannerImg", "static/images/banner_blog.jpg");
                User user = userService.findByAccount(key.replaceAll(App.BLOGJSON_PREFIX, ""));
                obj.put("avator", user == null ? "static/images/logo.jpg" : user.getAvator());
                jk.setValue(obj.toJSONString());
            } else {
                jk.setValue("{}");
            }
            jsonKeyValueDao.save(jk);
        }
        return jk.jsonObj();
    }

    @Override
    public JSONObject findById(String key) {
        JsonKeyValue jk = jsonKeyValueDao.findById(key);
        if (jk != null) {
            return jk.jsonObj();
        } else {
            return null;
        }
    }

    @Override
    public List<JsonKeyValue> findAll() {
        return jsonKeyValueDao.findAll();
    }
}
