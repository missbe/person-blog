package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.SystemLogDao;
import cn.missbe.missbe_www.entity.SystemLog;
import cn.missbe.missbe_www.entity.SystemLog.Level;
import cn.missbe.missbe_www.listener.AppConfig;
import cn.missbe.missbe_www.service.SystemLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemLogServiceImpl implements SystemLogService {
    @Resource
    private SystemLogDao systemLogDao;

    @Override
    public void saveLog(Level level, String username, String msg, String params, String url, String ip) {
        // 判断日志存储级别
        Level saveLevel = Level.strToLevel(AppConfig.getConfigAsString("system.log.saveDBLevel"));
        if (level.equals(Level.error) || Level.AmoreB(level, saveLevel)) {
            SystemLog log = new SystemLog();
            log.setLevel(level.toString());
            log.setMsg(msg);
            log.setIp(ip);
            log.setParams(params);
            log.setUrl(url);
            log.setUsername(username);
            systemLogDao.save(log);
        }
    }
}
