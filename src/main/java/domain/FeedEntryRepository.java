package domain;

import com.google.common.collect.*;
import com.google.common.primitives.Longs;
import com.sun.syndication.feed.synd.SyndEntry;
import fetcher.FeedFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

@Repository
public class FeedEntryRepository {

    private static final String URL_PATTERN = "http://blogit.kauppalehti.fi/evs/aid/%d/recent/10/";
    private static final Set<String> URLS = Sets.newHashSet();
    private static final ContiguousSet<Integer> FROM_FOUR_TO_SEVEN = Ranges.closed(4, 7).asSet(DiscreteDomains.integers());

    static {
        // The URL's of the selected feeds have just one difference which happens to be a set of ints ranging from 4 to 7
        for (Integer i : FROM_FOUR_TO_SEVEN) {
            URLS.add(String.format(URL_PATTERN, i));
        }
    }

    @Inject
    private FeedFetcher feedFetcher;

    private Logger logger = LoggerFactory.getLogger(FeedEntryRepository.class);

    /**
     * Fetches the entries of predefined feeds and combines them to one list
     * @return All unique entries sorted by publishing date
     */
    public List<SyndEntry> fetchEntries() {
        Set<SyndEntry> entries = Sets.newHashSet();
        for (String url : URLS) {
            entries.addAll(entriesFrom(url));
        }
        return new NewestFirst().sortedCopy(entries);
    }

    private List<SyndEntry> entriesFrom(String url) {
        try {
            return feedFetcher.fetchFrom(url);
        } catch (Exception e) {
            logger.warn(String.format("Fetching feed from URL '%s' failed", url), e);
            return Lists.newArrayList();
        }
    }

    private static class NewestFirst extends Ordering<SyndEntry> {
        @Override
        public int compare(SyndEntry first, SyndEntry second) {
            return -Longs.compare(first.getPublishedDate().getTime(), second.getPublishedDate().getTime());
        }
    }
}
