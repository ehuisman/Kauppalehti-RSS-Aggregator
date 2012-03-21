package web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HomeControllerTest {

    private HomeController controller;

    @Before
    public void setUp() throws Exception {
        controller = new HomeController();
    }

    @Test
    public void testHome() throws Exception {
        ModelAndView result = controller.home();
        assertEquals(result.getViewName(), "home");
        assertTrue(result.getModel().containsKey("target"));
    }
}
