import java.time.LocalDateTime;

/**
 * Complaint model stores all relevant details.
 */
public class Complaint implements Comparable<Complaint> {
    // Note: Since this enum is internal, no other imports are needed here.
    public enum Status { RECEIVED, UNDER_INVESTIGATION, RESOLVED, REJECTED }

    private final String complaintId;
    private final String title;
    private final String description;
    private final String reporterId;
    private final LocalDateTime createdAt;
    private int priority; // 1 high -> larger number lower priority or vice-versa
    private Status status;

    public Complaint(String complaintId, String title, String description, String reporterId, int priority) {
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.reporterId = reporterId;
        this.createdAt = LocalDateTime.now();
        this.priority = priority;
        this.status = Status.RECEIVED;
    }

    // Getters and setters
    public String getComplaintId() { return complaintId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getReporterId() { return reporterId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return String.format("ID:%s | %s | by:%s | %s | priority:%d | %s",
                complaintId, title, reporterId, createdAt.toString(), priority, status);
    }

    // Compare by complaint ID lexicographically for BST insertion/search
    @Override
    public int compareTo(Complaint other) {
        return this.complaintId.compareTo(other.complaintId);
    }
}