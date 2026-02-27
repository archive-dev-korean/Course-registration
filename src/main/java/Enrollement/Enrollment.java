package Enrollement;

import java.time.LocalDateTime;
// {
//   "enrollmentId": 100,
//   "courseId": 10,
//   "userId": 1,
//   "status": "ENROLLED",
//   "createdAt": "2026-02-20T17:20:00+09:00",
//   "canceledAt": null
// }
public class Enrollment {
    private int enrollmentId;
    private int courseId;
    private int userId;
    private String status;
    private String createdAt;
    private String canceledAt;
    
    //constructor
    public Enrollment(int enrollmentId, int courseId, int userId, String status, String createdAt, String canceledAt) {
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
