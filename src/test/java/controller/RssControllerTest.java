package controller;

import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import domain.FeedEntryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RssControllerTest {
    @InjectMocks
    private RssController controller = new RssController();

    @Mock
    private FeedEntryRepository repo;

    @Test
    public void testHome() throws Exception {
        when(repo.fetchEntries()).thenReturn(Lists.<SyndEntry>newArrayList(new SyndEntryImpl()));
        ModelAndView result = controller.home();
        assertEquals("Unexpected view name", result.getViewName(), "rss");
        assertTrue("Property \"entries\" missing from model", result.getModel().containsKey("entries"));
    }
}
