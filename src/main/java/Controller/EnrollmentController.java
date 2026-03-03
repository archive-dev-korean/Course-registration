package Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Arrays;
import entity.Enrollment;

@RestController
public class EnrollmentController {
    @RequestMapping(path = "/enrollments", method = RequestMethod.GET)
    public List<Enrollment> retrieveAllEnrollments() {
        return Arrays.asList(
            new Enrollment(100, 10, 1, "ENROLLED", "2026-02-20T17:20:00+09:00", null)
        );
    };
    @RequestMapping(path = "/enrollments/{courseId}", method = RequestMethod.GET)
    public Enrollment retrieveEnrollment(@PathVariable("courseId") int courseId) {
        return new Enrollment(100, courseId, 1, "ENROLLED", "2026-02-20T17:20:00+09:00", null);
    };
    @RequestMapping(path = "/courses/{courseId}/enrollments", method = RequestMethod.POST)
    public Enrollment createEnrollment(@PathVariable("courseId") int courseId) {
        return new Enrollment(200, courseId, 31, "UNENROLLED", "2026-02-20T17:20:00+09:00", null);
    };
}