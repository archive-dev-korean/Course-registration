package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * API 응답용 DTO (GET /courses, GET /courses/{id})
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

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

    public static CourseResponse from(entity.Course course) {
        if (course == null) return null;
        return new CourseResponse(
                course.getCourseId(),
                course.getTitle(),
                course.getInstructor(),
                course.getCategory(),
                course.getStartAt(),
                course.getEndAt(),
                course.getCapacity(),
                course.getEnrolledcount(),
                course.getStatus(),
                course.getVersion()
        );
    }
}
