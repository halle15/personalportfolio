package mmd.filters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

public class IPRateLimiter {

    private Map<String, Bucket> rateLimiters  = new ConcurrentHashMap<>();
    private Map<String, Long> lastAccessTime = new ConcurrentHashMap<>();

    public void addIP(String ip) {
        Bandwidth limit = Bandwidth.simple(3, Duration.ofMinutes(10));
        Bucket rateLimiter = Bucket.builder().addLimit(limit).build();
        rateLimiters.put(ip, rateLimiter);
    }

    public boolean tryAcquire(String ip) {
        Bucket rateLimiter = rateLimiters.get(ip);
        if (rateLimiter == null) {
            addIP(ip);
            rateLimiter = rateLimiters.get(ip);
        }
        lastAccessTime.put(ip, System.currentTimeMillis());
        return rateLimiter.tryConsume(1);
    }
    
    public void evictOldEntries(long maxAgeMillis) {
        long currentTime = System.currentTimeMillis();
        lastAccessTime.entrySet().removeIf(entry -> {
            if (currentTime - entry.getValue() > maxAgeMillis) {
                rateLimiters.remove(entry.getKey());
                return true;
            }
            return false;
        });
    }
}