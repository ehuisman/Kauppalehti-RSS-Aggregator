package view;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.sun.syndication.feed.rss.Channel;
import com.sun.syndication.feed.rss.Item;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.impl.ConverterForRSS20;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public class RssFeedView extends AbstractRssFeedView {
    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed, HttpServletRequest request) {
        feed.setTitle("Planet Kauppalehti");
        feed.setDescription("Aggregated RSS feed of blogs from kauppalehti.fi");
        feed.setLink("http://www.kauppalehti.fi/5/i/tilaukset/rss.jsp");
        feed.setEncoding("UTF-8");
        super.buildFeedMetadata(model, feed, request);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @SuppressWarnings("unchecked")
    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        return new RssConverter().convert((List<SyndEntry>) model.get("entries"));
    }

    private static class RssConverter extends ConverterForRSS20 {
        public List<Item> convert(List<SyndEntry> entries) {
            return Lists.transform(entries, new Function<SyndEntry, Item>() {
                @Override
                public Item apply(SyndEntry entry) {
                    return createRSSItem(entry);
                }
            });
        }
    }

}
