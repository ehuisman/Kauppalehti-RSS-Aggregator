package web;

import domain.FeedEntryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;

@Controller
public class HomeController {
    @Inject
    private FeedEntryRepository repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() throws Exception {
        return new ModelAndView("home", "entries", repo.fetchEntries());
    }

}
