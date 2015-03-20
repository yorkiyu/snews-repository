package com.yz.snews.crawler.live;


import java.util.Date;

import cn.edu.hfut.dmic.webcollector.model.Links;
import cn.edu.hfut.dmic.webcollector.model.Page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yz.snews.crawler.BaseCrawler;
import com.yz.snews.entity.Channel;
import com.yz.snews.entity.News;
import com.yz.snews.service.ChannelService;
import com.yz.snews.service.NewsService;
import com.yz.snews.util.Constants;
import com.yz.snews.util.DateUtil;
import com.yz.snews.util.DownImgUtil;

/**
 * WebCollector 2.x版本的SianCrawler
 */
@Component
@Scope("prototype")
public class SianLiveCrawler extends BaseCrawler {
	@Autowired
	private ChannelService channelService;
	@Autowired 
	private NewsService newsService;
	private int count = 0;
    public SianLiveCrawler() {
        super("e:/cdata/sian");
    }

	@Override
    public Links visitAndGetNextLinks(Page page) {
        Document doc = page.getDoc();
        Elements elements = doc.select(".bd_list .bd_i");
        Channel channel = channelService.findByCode("financeLive");
        for (Element element : elements) {
			Date editTime = DateUtil.timeStamp2Date(element.attr("data-time"));
			String title = element.select(".bd_i_txt_c").text();
			Elements imgElements = element.select(".bd_i_pics_tb_w img");
			if (!newsService.isExitByChannelIdAndEditTime(channel.getId(), editTime)) {
				News news = new News();
				news.setTitle(title);
				news.setEditTime(editTime);
				news.setType(0);
				news.setChannelId(channel.getId());
				downImgs(imgElements, news);
				newsService.save(news);
			}else {
				return null;
			}
		}
        return null;
    }
	private void downImgs(Elements imgElements,News news) {
		if (imgElements.size() == 0) {
			return;
		}
		try {
			String commonName = DateUtil.getTimeStamp() + Integer.toString(++count);
			if (count > 1000) {
				count = 0;
			}
			String sImgSrc = imgElements.get(0).attr("src");
			byte [] sImgData = DownImgUtil.getImage(sImgSrc);
			DownImgUtil.saveImg(sImgData, System.getProperty(Constants.WEB_APP_DIR)+"/sian/live/min/"+commonName+".jpg");
			String mImgSrc = imgElements.get(1).attr("src");
			byte [] mImgData = DownImgUtil.getImage(mImgSrc);
			DownImgUtil.saveImg(mImgData, System.getProperty(Constants.WEB_APP_DIR)+"/sian/live/max/"+commonName+".jpg");
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		
	}
}