package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.MessageDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.Message;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.service.MessageService;
import cn.missbe.missbe_www.util.PrintUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liaoxing
 * @date 16/8/15 20:10
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageDao messageDao;

    @Override
    public JsonBaseResult save(Message message) {
        if (message.getId() != 0) {
            return messageDao.update(message);
        }
        JsonBaseResult res = messageDao.save(message);
        if (res.isSuccess()) {
            PrintUtil.print("官网有新的留言信息！"+message.getIp()+"留言操作"+"留言内容：" + message.getMessage(), SystemLog.Level.error);
        }
        return res;
    }
}
