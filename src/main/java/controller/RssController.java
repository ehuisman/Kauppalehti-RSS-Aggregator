package controller;

import domain.FeedEntryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class RssController {
    @Inject
    private FeedEntryRepository repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() throws Exception {
        return new ModelAndView("rss", "entries", repo.fetchEntries());
    }

}
