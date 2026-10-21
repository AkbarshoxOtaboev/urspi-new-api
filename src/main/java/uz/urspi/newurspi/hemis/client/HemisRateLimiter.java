package uz.urspi.newurspi.hemis.client;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * HEMIS Backend API: max 10 requests per second (IP-based on their side).
 */
@Component
public class HemisRateLimiter {

    private static final long MIN_INTERVAL_NANOS = TimeUnit.MILLISECONDS.toNanos(100);

    private final Object lock = new Object();
    private long nextAllowedNanos = 0L;

    public void acquire() {
        synchronized (lock) {
            long now = System.nanoTime();
            if (now < nextAllowedNanos) {
                LockSupport.parkNanos(nextAllowedNanos - now);
                now = System.nanoTime();
            }
            nextAllowedNanos = Math.max(nextAllowedNanos + MIN_INTERVAL_NANOS, now + MIN_INTERVAL_NANOS);
        }
    }
}
