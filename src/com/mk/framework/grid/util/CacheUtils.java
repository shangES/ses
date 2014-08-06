package com.mk.framework.grid.util;

import java.util.Map;

public class CacheUtils {

	public static Object getFromCache(Map<?, ?> cache, Object key){
		return cache.get(key);
	}
	public static void addToCache(Map<Object, Object> cache, Object key, Object obj){
		cache.put(key,obj);
	}
}
