package cn.missbe.missbe_www.service.impl;

import cn.missbe.missbe_www.dao.NewsDao;
import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.News;
import cn.missbe.missbe_www.service.NewsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liaoxing
 * @date 16/8/14 14:31
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Resource
    private NewsDao newsDao;

    @Override
    public JsonBaseResult saveNews(News news) {
        if (news.getId() != 0) {
            return newsDao.update(news);
        } else {
            return newsDao.save(news);
        }
    }
}
