/**
 *  Copyright 2011-2013 Terracotta, Inc.
 *  Copyright 2011-2013 Oracle America Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.jsr107.ri.annotations;

import jakarta.cache.Cache;
import jakarta.cache.CacheManager;
import jakarta.cache.Caching;
import jakarta.cache.annotation.CacheMethodDetails;
import jakarta.cache.annotation.CacheResolver;
import jakarta.cache.annotation.CacheResolverFactory;
import jakarta.cache.annotation.CacheResult;
import jakarta.cache.configuration.MutableConfiguration;
import jakarta.cache.spi.CachingProvider;
import java.lang.annotation.Annotation;
import java.util.logging.Logger;

/**
 * Default {@link CacheResolverFactory} that uses the default {@link CacheManager} and finds the {@link Cache}
 * using {@link CacheManager#getCache(String)}. Returns a {@link DefaultCacheResolver} that wraps the found
 * {@link Cache}
 *
 * @author Eric Dalquist
 * @author Rick Hightower
 * @since 1.0
 */
public class DefaultCacheResolverFactory implements CacheResolverFactory {
  private final Logger logger = Logger.getLogger(this.getClass().getName());

  private final CacheManager cacheManager;

  /**
   * Constructs the resolver
   *
   * @param cacheManager the cache manager to use
   */
  public DefaultCacheResolverFactory(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  /**
   * Constructs the resolver
   */
  public DefaultCacheResolverFactory() {
    CachingProvider provider = Caching.getCachingProvider();
    this.cacheManager = provider.getCacheManager(provider.getDefaultURI(), provider.getDefaultClassLoader());
  }

  /* (non-Javadoc)
   * @see jakarta.cache.annotation.CacheResolverFactory#getCacheResolver(jakarta.cache.annotation.CacheMethodDetails)
   */
  @Override
  public CacheResolver getCacheResolver(CacheMethodDetails<? extends Annotation> cacheMethodDetails) {
    final String cacheName = cacheMethodDetails.getCacheName();
    Cache<?, ?> cache = this.cacheManager.getCache(cacheName);

    if (cache == null) {
      logger.warning("No Cache named '" + cacheName + "' was found in the CacheManager, a default cache will be created.");
      cacheManager.createCache(cacheName, new MutableConfiguration<Object, Object>());
      cache = cacheManager.getCache(cacheName);
    }

    return new DefaultCacheResolver(cache);
  }

  @Override
  public CacheResolver getExceptionCacheResolver(CacheMethodDetails<CacheResult> cacheMethodDetails) {
    final CacheResult cacheResultAnnotation = cacheMethodDetails.getCacheAnnotation();
    final String exceptionCacheName = cacheResultAnnotation.exceptionCacheName();
    if (exceptionCacheName == null || exceptionCacheName.trim().length() == 0) {
      throw new IllegalArgumentException("Can only be called when CacheResult.exceptionCacheName() is specified");
    }

    Cache<?, ?> cache = cacheManager.getCache(exceptionCacheName);

    if (cache == null) {
      logger.warning("No Cache named '" + exceptionCacheName +
          "' was found in the CacheManager, a default cache will be created.");
      cacheManager.createCache(exceptionCacheName, new MutableConfiguration<Object, Object>());
      cache = cacheManager.getCache(exceptionCacheName);
    }

    return new DefaultCacheResolver(cache);
  }
}
