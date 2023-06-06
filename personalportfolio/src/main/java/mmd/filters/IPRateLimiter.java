package mmd.filters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

public class IPRateLimiter {

    private Map<String, Bucket> rateLimiters;

    public IPRateLimiter() {
    	System.out.println("built");
        this.rateLimiters = new HashMap<>();
    }

    public void addIP(String ip) {
        Bandwidth limit = Bandwidth.simple(3, Duration.ofMinutes(10));
        Bucket rateLimiter = Bucket.builder().addLimit(limit).build();
        rateLimiters.put(ip, rateLimiter);
    }

    public boolean tryAcquire(String ip) {
    	System.out.println("acq");
        Bucket rateLimiter = rateLimiters.get(ip);
        if (rateLimiter == null) {
            addIP(ip);
            rateLimiter = rateLimiters.get(ip);
        }
        return rateLimiter.tryConsume(1);
    }

}