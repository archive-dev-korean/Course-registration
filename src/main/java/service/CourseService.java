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
