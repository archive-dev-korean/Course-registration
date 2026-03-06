// package jdbc;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import org.springframework.beans.factory.annotation.Autowired;
// import Repository.CourseJdbcRepository;
// import course.Course;
// @Component
// public class CourseJdbcCommandLineRunner implements CommandLineRunner {

//     @Autowired
//     private CourseJdbcRepository repository;

//     @Override
//     public void run(String... args) throws Exception {
//         System.out.println(">>> CourseJdbcCommandLineRunner: creating table and inserting data");
//         repository.createTable();
//         // repository.update(new Course(10, "Spring Boot심화123", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 0, "OPEN"));
//         // repository.delete(10);
//         repository.insert(new Course(10, "Spring Boot심화123", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 0, "OPEN"));
//         repository.insert(new Course(11, "Spring Boot심화13", "Kim", "BACKEND", "2026-03-02T10:00:00+09:00", "2026-03-02T12:00:00+09:00", 30, 0, "OPEN"));
//         System.out.println(">>> CourseJdbcCommandLineRunner: done");
//     }
// }

