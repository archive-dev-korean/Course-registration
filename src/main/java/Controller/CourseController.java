package Controller;
import java.util.List;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import course.Course;
//course
//     {
//   "courseId": 10,
//   "title": "Spring Boot 심화",
//   "instructor": "Kim",
//   "category": "BACKEND",
//   "startAt": "2026-03-02T10:00:00+09:00",
//   "endAt": "2026-03-02T12:00:00+09:00",
//   "capacity": 30,
//   "enrolled": 28,
//   "status": "OPEN"
// }
@RestController
public class CourseController {

    @GetMapping(path = "/courses")
    public List<Course> retrieveAllCourses() {
        return Arrays.asList(
            new Course(10, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 28, "OPEN")
        //     new Course(11, "Spring Boot 기초", "Lee", "FRONTEND", "2026-03-03T10:00:00+09:00", "2026-03-03T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(12, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-04T10:00:00+09:00", "2026-03-04T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(13, "Spring Boot 기초", "Lee", "FRONTEND", "2026-03-05T10:00:00+09:00", "2026-03-05T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(14, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-06T10:00:00+09:00", "2026-03-06T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(15, "Spring Boot 기초", "Lee", "FRONTEND", "2026-03-07T10:00:00+09:00", "2026-03-07T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(16, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-08T10:00:00+09:00", "2026-03-08T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(17, "Spring Boot 기초", "Lee", "FRONTEND", "2026-03-09T10:00:00+09:00", "2026-03-09T12:00:00+09:00", 30, 28, "OPEN"),
        //     new Course(18, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-10T10:00:00+09:00", "2026-03-10T12:00:00+09:00", 30, 28, "OPEN"),
        );
    }
    @GetMapping(path = "/courses/{courseId}")
    public Course retrieveCourse(@PathVariable("courseId") int courseId) {
        return new Course(courseId, "Spring Boot 심화", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 28, "OPEN");
    }
    @GetMapping(path="/courses/{courseId}/capacity")
    public int retrieveCourseCapacity(@PathVariable("courseId") int courseId) {
        return 30;
    }
}