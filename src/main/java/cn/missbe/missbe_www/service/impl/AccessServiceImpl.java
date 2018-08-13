package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.AccessDao;
import cn.missbe.missbe_www.entity.Access;
import cn.missbe.missbe_www.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liaoxing
 * @date 16/8/16 19:25
 */
@Service
public class AccessServiceImpl implements AccessService {
    @Autowired
    private AccessDao accessDao;


    @Override
    public void save(Access access) {
        accessDao.save(access);
    }
}
