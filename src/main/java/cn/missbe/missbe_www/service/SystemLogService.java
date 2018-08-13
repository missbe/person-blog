package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.entity.SystemLog.Level;

public interface SystemLogService {

    /**
     * 记录日志，根据配置决定是否持久化
     *
     * @param level    日志等级
     * @param username 操作用户
     * @param msg      日志信息
     * @param params   参数信息
     * @param url      来源信息
     * @param ip       ip信息
     */
    void saveLog(Level level, String username, String msg, String params, String url, String ip);

}
