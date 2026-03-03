package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Enrollment {

    @Id
    private int enrollmentId;
    private int courseId;
    private int userId;
    private String status;
    private String createdAt;
    private String canceledAt;

    // public Enrollment() {
    // }

    public Enrollment(int enrollmentId, int courseId, int userId,
                      String status, String createdAt, String canceledAt) {
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
    }

    //getters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUserId() {
        return userId;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCanceledAt() {
        return canceledAt;
    }

    //toString
    @Override
    public String toString() {
        return "Enrollment{" +
                "enrollmentId=" + enrollmentId +
                ", courseId=" + courseId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", canceledAt='" + canceledAt + '\'' +
                '}';
    }
}

