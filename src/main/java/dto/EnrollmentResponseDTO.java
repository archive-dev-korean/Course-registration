package dto;

import entity.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDTO {
    private String enrollmentId;
    private int courseId;
    private int userId;
    private String status;
    private String createdAt;
    private String canceledAt;
    private String reason;

    public static EnrollmentResponseDTO from(entity.Enrollment enrollment) {
        if (enrollment == null) return null;
        return new EnrollmentResponseDTO(
            enrollment.getEnrollmentId(),
            enrollment.getCourseId(),
            enrollment.getUserId(),
            enrollment.getStatus(),
            enrollment.getCreatedAt(),
            enrollment.getCanceledAt(),
            enrollment.getReason());
    }
}