package Repository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import entity.Course;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
@Transactional //JPA로 쿼리를 실행할 떄마다 트랜잭션 처리해야 됨
public class CoursJpaRepository {
    @Autowired //대신 @PersistenceContext 사용 가능
    private EntityManager entityManager;

    public void insert(Course course) {
        entityManager.merge(course); //Entity에 merge해줌
    }
    public Course create(Course course) {
        entityManager.persist(course); //create
        return course;
    }
    public Course findById(int id) {
        return entityManager.find(Course.class, id);
    }
    public List<Course> findAll() {
        return entityManager.createQuery("SELECT c FROM Course c", Course.class).getResultList();
    }
    //Delete
    // public void deleteById(int id){
    //     Course course = entityManager.find(Course.class, id);
    //     entityManager.remove(course);
    //     // return course;
    // }
}