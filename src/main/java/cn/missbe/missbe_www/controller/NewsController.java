package cn.missbe.missbe_www.controller;

import cn.missbe.missbe_www.dto.JsonBaseResult;
import cn.missbe.missbe_www.entity.News;
import cn.missbe.missbe_www.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author lyg
 * @date 16/8/14 17:10
 */

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    @Resource
    private NewsService newsService;

    @RequestMapping(value = "add", name = "新闻添加修改")
    @ResponseBody
    public JsonBaseResult save(News news) {
        return newsService.saveNews(news);
    }

}
