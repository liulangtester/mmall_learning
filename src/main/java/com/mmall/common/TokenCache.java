package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by root on 10/20/19.
 */
public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //初始缓存为1000，最大缓存为10000，当超过最大缓存时，使用LRU算法清除缓存，缓存有效期为12天
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现方法，当调用get取值是，如果key没有对应的值，就调用此方法进行加载
                @Override
                public String load(String key) throws Exception {
                    return "null";//使用字符串null是为了防止null.equals()空指针
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key,value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        }catch (Exception e){
            logger.error("lovalCache get error", e);
        }
        return null;
    }
}