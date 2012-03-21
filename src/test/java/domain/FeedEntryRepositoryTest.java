package domain;

import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import fetcher.FeedFetcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FeedEntryRepositoryTest {
    @InjectMocks
    FeedEntryRepository repository = new FeedEntryRepository();

    @Mock
    FeedFetcher fetcher;

    @Test
    public void callsFetcherWithFourUrls() throws Exception {
        repository.fetchEntries();
        verify(fetcher, times(4)).fetchFrom(anyString());
    }

    @Test
    public void returnsNewestEntriesFirst() throws Exception {
        Date date = new Date();
        when(fetcher.fetchFrom(anyString()))
                .thenReturn(Lists.newArrayList(entry(new Date(date.getTime() - 1)), entry(date)))
                .thenReturn(Lists.<SyndEntry>newArrayList());
        List<SyndEntry> entries = repository.fetchEntries();
        assertThat(entries.size(), is(2));
        assertThat(entries.get(0).getPublishedDate(), is(date));
    }

    @Test
    public void filtersOutDuplicateEntries() throws Exception {
        Date date = new Date();
        when(fetcher.fetchFrom(anyString())).thenReturn(Lists.newArrayList(entry(date)));
        assertThat(repository.fetchEntries().size(), is(1));
    }

    @Test
    public void continuesOnError() throws Exception {
        //noinspection unchecked
        when(fetcher.fetchFrom(anyString()))
                .thenReturn(Lists.newArrayList(entry(new Date())))
                .thenThrow(IOException.class)
                .thenReturn(Lists.newArrayList(entry(new Date())));
        repository.fetchEntries();
        verify(fetcher, times(4)).fetchFrom(anyString());
    }

    private static SyndEntry entry(final Date date) {
        SyndEntry newEntry = new SyndEntryImpl();
        newEntry.setPublishedDate(date);
        return newEntry;
    }
}
