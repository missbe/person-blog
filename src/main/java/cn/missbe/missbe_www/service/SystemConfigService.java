package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.entity.SystemConfig;

import java.util.List;

public interface SystemConfigService {

    /**
     * 同步文件配置到数据库<br>
     * 如果数据库中没有：则新增到数据<br>
     * 如果存在：则根据是否线上模式判断是否需要覆盖配置信息<br>
     * 如果数据库中多余：则删除该配置
     *
     * @param configsInfile 来自配置文件的配置列表
     */
    void syncConfig(List<SystemConfig> configsInfile);

    SystemConfig findById(String key);

    void save(SystemConfig config);

}
