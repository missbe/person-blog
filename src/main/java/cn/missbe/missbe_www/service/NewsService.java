package cn.missbe.missbe_www.service;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.News;

/**
 * @author liaoxing
 * @date 16/8/14 14:30
 */
public interface NewsService {
    JsonBaseResult saveNews(News news);
}
