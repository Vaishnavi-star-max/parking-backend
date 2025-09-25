package com.example.parking.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RateLimitingService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket createNewBucket() {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(1000, Refill.intervally(1000, Duration.ofMinutes(1))))
                .build();
    }

    public Bucket resolveBucket(String key) {
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

    public boolean tryConsume(String key) {
        return resolveBucket(key).tryConsume(1);
    }

    public boolean tryConsume(String key, long tokens) {
        return resolveBucket(key).tryConsume(tokens);
    }
}