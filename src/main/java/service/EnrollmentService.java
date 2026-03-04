package service;

import entity.Course;
import entity.Enrollment;
import Repository.EnrollmentJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentJpaRepository enrollmentJpaRepository;

    @Autowired
    private CourseService courseService;

    public List<Enrollment> findAll() {
        return enrollmentJpaRepository.findAll();
    }

    public Enrollment findById(int id) {
        return enrollmentJpaRepository.findById(id);
    }

    /** 수강 신청 - 명세: 409 ALREADY_ENROLLED, CAPACITY_FULL, ENROLLMENT_CLOSED / 404 COURSE_NOT_FOUND */
    @Transactional
    public Enrollment enroll(int courseId, int userId) {
        Course course = courseService.findById(courseId);
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "COURSE_NOT_FOUND");
        }
        Enrollment existing = enrollmentJpaRepository.findByCourseIdAndUserId(courseId, userId);
        if (existing != null && "ENROLLED".equals(existing.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ALREADY_ENROLLED");
        }
        long enrolledCount = enrollmentJpaRepository.countByCourseId(courseId);
        if (enrolledCount >= course.getCapacity()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CAPACITY_FULL");
        }
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Enrollment enrollment = new Enrollment(courseId, userId, "ENROLLED", now, null, null);
        enrollment.setEnrollmentId(UUID.randomUUID().toString());
        return enrollmentJpaRepository.create(enrollment);
    }

    /** 수강 취소 - 명세: 404 ENROLLMENT_NOT_FOUND, 409 CANCEL_NOT_ALLOWED */
    @Transactional
    public Enrollment cancel(int courseId, int userId) {
        Enrollment enrollment = enrollmentJpaRepository.findByCourseIdAndUserId(courseId, userId);
        if (enrollment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ENROLLMENT_NOT_FOUND");
        }
        if (!"ENROLLED".equals(enrollment.getStatus())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CANCEL_NOT_ALLOWED");
        }
        enrollment.setStatus("CANCELED");
        enrollment.setCanceledAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        return enrollmentJpaRepository.merge(enrollment);
    }

    /** 내 신청/취소 이력 조회 (페이징) */
    public List<Enrollment> findMyEnrollments(int userId, int page, int size, String status) {
        return enrollmentJpaRepository.findAllByUserId(userId, status, page, size);
    }

    public long countMyEnrollments(int userId, String status) {
        return enrollmentJpaRepository.countByUserId(userId, status);
    }

    /** 강의별 신청자 목록 (ADMIN용, 페이징) */
    public List<Enrollment> findEnrollmentsByCourseId(int courseId, int page, int size) {
        return enrollmentJpaRepository.findAllByCourseId(courseId, page, size);
    }

    public long countEnrollmentsByCourseId(int courseId) {
        return enrollmentJpaRepository.countByCourseIdForAdmin(courseId);
    }
}
