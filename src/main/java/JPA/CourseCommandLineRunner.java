package JPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import Repository.CoursJpaRepository;
import entity.Course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CourseCommandLineRunner implements CommandLineRunner {
    @Autowired
    private CoursJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {
        String startAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String endAt = LocalDateTime.now().plusHours(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        repository.insert(new Course(1, "JPA", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(2, "JPA2", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(3, "JPA3", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(4, "JPA4", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(5, "JPA5", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(6, "JPA6", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(7, "JPA7", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(8, "JPA8", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(9, "JPA9", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
        repository.insert(new Course(10, "JPA10", "Kim", "BACKEND", startAt, endAt, 30, 0, "OPEN", 0));
    }
}