package fetcher;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class FeedFetcher {
    @SuppressWarnings("unchecked")
    @Cacheable("default")
    public List<SyndEntry> fetchFrom(String url) throws FeedException, IOException {
        return new SyndFeedInput().build(new XmlReader(new URL(url))).getEntries();
    }
}
