package domain;

import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.primitives.Longs;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.io.FeedException;
import fetcher.FeedFetcher;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Repository
public class FeedEntryRepository {

    public static final String URL_PATTERN = "http://blogit.kauppalehti.fi/evs/aid/%d/recent/10/";

    @Inject
    private FeedFetcher feedFetcher;

    public List<SyndEntry> fetchEntries() throws FeedException, IOException {
        Set<SyndEntry> entries = Sets.newHashSet();
        for (int i = 4; i < 8; i++) {
            String url = String.format(URL_PATTERN, i);
            entries.addAll(feedFetcher.fetchFrom(url));
        }
        return new NewestFirst().sortedCopy(entries);
    }

    private static class NewestFirst extends Ordering<SyndEntry> {
        @Override
        public int compare(SyndEntry first, SyndEntry second) {
            return -Longs.compare(first.getPublishedDate().getTime(), second.getPublishedDate().getTime());
        }
    }
}
