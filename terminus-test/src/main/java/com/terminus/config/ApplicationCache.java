package com.terminus.config;

import java.util.List;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationCache implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

	@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
		cacheManager.setCacheNames(List.of("users"));
	}

}
