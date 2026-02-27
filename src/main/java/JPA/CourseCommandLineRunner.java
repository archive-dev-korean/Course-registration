package JPA;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import Repository.CoursJpaRepository;
import course.Course;
import org.springframework.boot.CommandLineRunner;


@Component
public class CourseCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CoursJpaRepository repository;
    @Override
    public void run(String... args) throws Exception {
        repository.create(new Course(10, "JPA TEST", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 0, "OPEN"));
        repository.insert(new Course(110, "JPA TEST2", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 0, "OPEN"));
    }
}