package config;

import fetcher.FeedFetcher;
import net.sf.ehcache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public FeedFetcher feedFetcher() {
        return new FeedFetcher();
    }

    @Bean
    public CacheManager cacheManager() {
        EhCacheCacheManager cacheManager = new EhCacheCacheManager();
        cacheManager.setCacheManager(initEhCacheManager());
        return cacheManager;
    }

    private net.sf.ehcache.CacheManager initEhCacheManager() {
        net.sf.ehcache.CacheManager cm = net.sf.ehcache.CacheManager.create();
        cm.addCache(new Cache("feeds", 4, false, false, 600, 0));
        return cm;
    }
}
