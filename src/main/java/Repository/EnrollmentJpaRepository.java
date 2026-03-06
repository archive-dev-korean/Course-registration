package Repository;

import entity.Enrollment;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EnrollmentJpaRepository {

    @Autowired
    private EntityManager entityManager;

    public Enrollment create(Enrollment enrollment) {
        entityManager.persist(enrollment);
        return enrollment;
    }

    public Enrollment findById(String id) {
        return entityManager.find(Enrollment.class, id);
    }

    public List<Enrollment> findAll() {
        return entityManager.createQuery("SELECT e FROM Enrollment e", Enrollment.class).getResultList();
    }

    public Enrollment findByCourseIdAndUserId(int courseId, int userId) {
        List<Enrollment> list = entityManager.createQuery(
                        "SELECT e FROM Enrollment e WHERE e.courseId = :courseId AND e.userId = :userId", Enrollment.class)
                .setParameter("courseId", courseId)
                .setParameter("userId", userId)
                .getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    public long countByCourseId(int courseId) {
        return entityManager.createQuery("SELECT COUNT(e) FROM Enrollment e WHERE e.courseId = :courseId AND e.status = 'ENROLLED'", Long.class)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }

    public List<Enrollment> findAllByUserId(int userId, String status, int page, int size) {
        String jpql = "SELECT e FROM Enrollment e WHERE e.userId = :userId";
        if (status != null && !status.isBlank()) {
            jpql += " AND e.status = :status";
        }
        jpql += " ORDER BY e.createdAt DESC";
        var q = entityManager.createQuery(jpql, Enrollment.class).setParameter("userId", userId);
        if (status != null && !status.isBlank()) {
            q.setParameter("status", status);
        }
        return q.setFirstResult(page * size).setMaxResults(size).getResultList();
    }

    public long countByUserId(int userId, String status) {
        String jpql = "SELECT COUNT(e) FROM Enrollment e WHERE e.userId = :userId";
        if (status != null && !status.isBlank()) {
            jpql += " AND e.status = :status";
        }
        var q = entityManager.createQuery(jpql, Long.class).setParameter("userId", userId);
        if (status != null && !status.isBlank()) {
            q.setParameter("status", status);
        }
        return q.getSingleResult();
    }

    public List<Enrollment> findAllByCourseId(int courseId, int page, int size) {
        return entityManager.createQuery("SELECT e FROM Enrollment e WHERE e.courseId = :courseId ORDER BY e.createdAt DESC", Enrollment.class)
                .setParameter("courseId", courseId)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long countByCourseIdForAdmin(int courseId) {
        return entityManager.createQuery("SELECT COUNT(e) FROM Enrollment e WHERE e.courseId = :courseId", Long.class)
                .setParameter("courseId", courseId)
                .getSingleResult();
    }

    public Enrollment merge(Enrollment enrollment) {
        return entityManager.merge(enrollment);
    }
}