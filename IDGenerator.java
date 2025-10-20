import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple ID generator for complaints and users
 */
public class IDGenerator {
    private static AtomicInteger compCounter = new AtomicInteger(1000);
    public static String nextComplaintId() {
        return "C" + compCounter.getAndIncrement();
    }
    // user ids if needed
    private static AtomicInteger userCounter = new AtomicInteger(2000);
    public static String nextUserId() {
        return "U" + userCounter.getAndIncrement();
    }
}