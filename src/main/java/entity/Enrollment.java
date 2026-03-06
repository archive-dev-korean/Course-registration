package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * uq_course_user: DB의 unique 제약 이름 (course_id + user_id 조합이 유일).
 * 엔티티에는 컬럼으로 두지 않고 @Table(uniqueConstraints)로만 표현.
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "enrollment", uniqueConstraints = {
        @UniqueConstraint(name = "uq_course_user", columnNames = {"course_id", "user_id"})
})
public class Enrollment {

    @Id
    @Column(name = "enrollment_id", length = 36)
    private String enrollmentId;

    @Column(name = "course_id")
    private int courseId;

    @Column(name = "user_id")
    private int userId;
    @Column(name = "status")
    private String status;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "canceled_at")
    private String canceledAt;
    @Column(name = "reason")
    private String reason;

    /** JPA용 */
    public Enrollment() {
    }

    /** 신규 수강 신청 시에만 사용 (enrollmentId는 DB에서 생성) */
    public Enrollment(int courseId, int userId, String status, String createdAt, String canceledAt, String reason) {
        this.courseId = courseId;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
        this.reason = reason;
    }

    //getters
    //toString
    // @Override
    // public String toString() {
    //     return "Enrollment{" +
    //             "enrollmentId=" + enrollmentId +
    //             ", courseId=" + courseId +
    //             ", userId=" + userId +
    //             ", status='" + status + '\'' +
    //             ", createdAt='" + createdAt + '\'' +
    //             ", canceledAt='" + canceledAt + '\'' +
    //             ", reason='" + reason + '\'' +
    //             ", Uq_course_user='" + Uq_course_user + '\'' +
    //             '}';
    // }
    // @ToString 적용 시 위의 toString 메서드 사용 안함
}

