package config;

import domain.FeedEntryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;
import view.RssFeedView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "controller")
@Import(CacheConfig.class)
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public BeanNameViewResolver configureBeanNameViewResolver() {
        return new BeanNameViewResolver();
    }

    @Bean(name = "rss")
    public AbstractRssFeedView feedView() {
        return new RssFeedView();
    }

    @Bean
    public FeedEntryRepository feedEntryRepository() {
        return new FeedEntryRepository();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

}
