package Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import entity.Course;
import service.CourseService;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/courses")
    public List<Course> retrieveAllCourses() {
        return courseService.findAll();
    }

    @GetMapping(path = "/courses/{courseId}")
    public Course retrieveCourse(@PathVariable("courseId") int courseId) {
        return courseService.findById(courseId);
    }

    @GetMapping(path = "/courses/{courseId}/capacity")
    public int retrieveCourseCapacity(@PathVariable("courseId") int courseId) {
        return courseService.getCapacity(courseId);
    }
    @PostMapping(path = "/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseService.save(course);
    }
}