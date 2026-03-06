package service;

import entity.Course;
import Repository.CoursJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CoursJpaRepository courseJpaRepository;

    public List<Course> findAll() {
        return courseJpaRepository.findAll();
    }

    /** status가 null/빈값이면 전체 강의, 아니면 해당 status 강의만 (DB에 String으로 저장된 값 사용) */
    public List<Course> findCoursesByStatus(String status) {
        return courseJpaRepository.findAllByStatus(status);
    }

    public Course findById(int courseId) {
        return courseJpaRepository.findById(courseId);
    }

    public int getCapacity(int courseId) {
        Course course = courseJpaRepository.findById(courseId);
        return course == null ? 0 : course.getCapacity();
    }

    /** POST 요청으로 받은 강의를 DB에 저장 */
    public Course save(Course course) {
        return courseJpaRepository.create(course);
    }
}
