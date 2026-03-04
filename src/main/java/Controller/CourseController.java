package Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import dto.CourseCreateRequest;
import dto.CourseResponse;
import entity.Course;
import service.CourseService;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    /** 전체 강의 조회. ?status=OPEN 처럼 넣으면 해당 상태 강의만 조회 */
    @GetMapping(path = "/courses")
    public List<CourseResponse> retrieveAllCourses(@RequestParam(required = false) String status) {
        List<Course> courses = (status != null && !status.isBlank())
                ? courseService.findCoursesByStatus(status)
                : courseService.findAll();
        return courses.stream().map(CourseResponse::from).collect(Collectors.toList());
    }

    @GetMapping(path = "/courses/{courseId}")
    public CourseResponse retrieveCourse(@PathVariable("courseId") int courseId) {
        Course course = courseService.findById(courseId);
        return CourseResponse.from(course);
    }

    @GetMapping(path = "/courses/{courseId}/capacity")
    public int retrieveCourseCapacity(@PathVariable("courseId") int courseId) {
        return courseService.getCapacity(courseId);
    }

    @PostMapping(path = "/courses")
    public CourseResponse createCourse(@RequestBody CourseCreateRequest request) {
        Course saved = courseService.save(request.toEntity());
        return CourseResponse.from(saved);
    }
}