package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * API 요청용 DTO (POST /courses)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateRequest {

    private int courseId;
    private String title;
    private String instructor;
    private String category;
    private String startAt;
    private String endAt;
    private int capacity;
    private int enrolledcount;
    private String status;
    private int version;

    public entity.Course toEntity() {
        return new entity.Course(
                courseId, title, instructor, category,
                startAt, endAt, capacity, enrolledcount,
                status, version
        );
    }
}
