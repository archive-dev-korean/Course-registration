package Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dto.ApiResponse;
import dto.EnrollmentResponseDTO;
import dto.PageResponse;
import entity.Enrollment;
import service.EnrollmentService;

/**
 * 명세 기반 수강 신청 API
 * - 인증: 개발용 X-User-Id (실제 인증 연동 시 교체)
 * - ADMIN: X-Admin: true
 */
@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /** 1. 수강 신청 - POST /courses/{courseId}/enrollments, 201 Created */
    /** Body 없이 호출 가능 (ERD에 note 없음) */
    @PostMapping(path = "/courses/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> enroll(
            @PathVariable int courseId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") int userId) {
        Enrollment saved = enrollmentService.enroll(courseId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.of(EnrollmentResponseDTO.from(saved)));
    }

    /** 2. 수강 취소 - DELETE /courses/{courseId}/enrollments/me, 200 OK */
    @DeleteMapping(path = "/courses/{courseId}/enrollments/me")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> cancel(
            @PathVariable int courseId,
            @RequestHeader(value = "X-User-Id", defaultValue = "1") int userId) {
        Enrollment canceled = enrollmentService.cancel(courseId, userId);
        return ResponseEntity.ok(ApiResponse.of(EnrollmentResponseDTO.from(canceled)));
    }

    /** 3. 내 신청/취소 이력 조회 - GET /me/enrollments?page=0&size=20&status=ENROLLED, 200 OK */
    @GetMapping(path = "/me/enrollments")
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentResponseDTO>>> getMyEnrollments(
            @RequestHeader(value = "X-User-Id", defaultValue = "1") int userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String status) {
        if (page < 0 || size < 1 || size > 100) {
            return ResponseEntity.badRequest().build();
        }
        if (status != null && !status.isBlank() && !"ENROLLED".equals(status) && !"CANCELED".equals(status)) {
            return ResponseEntity.badRequest().build();
        }
        List<Enrollment> list = enrollmentService.findMyEnrollments(userId, page, size, status);
        long total = enrollmentService.countMyEnrollments(userId, status);
        List<EnrollmentResponseDTO> items = list.stream().map(EnrollmentResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.of(new PageResponse<>(items, page, size, total)));
    }

    /** 4. 강의별 신청자 목록 (ADMIN) - GET /courses/{courseId}/enrollments?page=0&size=20, 200 OK */
    @GetMapping(path = "/courses/{courseId}/enrollments")
    public ResponseEntity<ApiResponse<PageResponse<EnrollmentResponseDTO>>> getEnrollmentsByCourse(
            @PathVariable int courseId,
            @RequestHeader(value = "X-Admin", defaultValue = "false") boolean admin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        if (!admin) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (page < 0 || size < 1 || size > 100) {
            return ResponseEntity.badRequest().build();
        }
        List<Enrollment> list = enrollmentService.findEnrollmentsByCourseId(courseId, page, size);
        long total = enrollmentService.countEnrollmentsByCourseId(courseId);
        List<EnrollmentResponseDTO> items = list.stream().map(EnrollmentResponseDTO::from).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.of(new PageResponse<>(items, page, size, total)));
    }
}
