package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.Message;

/**
 * @author liaoxing
 * @date 16/8/15 20:10
 */
public interface MessageService {
    JsonBaseResult save(Message message);
}
