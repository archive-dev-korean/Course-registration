package dto;

import entity.Enrollment;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 수강 신청 요청 body (POST /enrollments 또는 POST /enrollments/{enrollmentId})
 * 예시: { "courseId": 10, "userId": 1, "status": "ENROLLED", "createdAt": "2026-03-02T10:00:00", "canceledAt": null, "reason": null }
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentCreateDTO {

    private int courseId;
    private int userId;
    private String status;
    private String createdAt;
    private String canceledAt;
    private String reason;

    /** 신규 등록용 (enrollmentId는 UUID로 생성) */
    public Enrollment toEntity() {
        Enrollment e = new Enrollment(courseId, userId, status, createdAt, canceledAt, reason);
        e.setEnrollmentId(UUID.randomUUID().toString());
        return e;
    }
}