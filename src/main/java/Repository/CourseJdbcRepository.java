package Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class CourseJdbcRepository {

    @Autowired
    private JdbcTemplate springjdbcTemplate;
    private static String CREATE_COURSE_SQL = "create table course (courseId int primary key, title varchar(255), instructor varchar(255), category varchar(255), startAt datetime, endAt datetime, capacity int, enrolled int, status varchar(255))";
    private static String INSERT_COURSE_SQL = "insert into course (courseId, title, instructor, category, startAt, endAt, capacity, enrolled,status) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    // private static String DELETE_COURSE_SQL = "delete from course where courseId = ?";
    // private static Strign UPDATE_COURSE_SQL = "update course set title = ?, instructor = ?, category = ?, startAt = ?, endAt = ?, capacity = ?, enrolled = ?, status = ? where courseId = ?";
    // public void update(course.Course course) {
    //     springjdbcTemplate.update(UPDATE_COURSE_SQL, course.getCourseId(), course.getTitle(), course.getInstructor(), course.getCategory(), course.getStartAt(), course.getEndAt(), course.getCapacity(), course.getEnrolled(), course.getStatus());
    // }
    public void createTable() {
        springjdbcTemplate.update(CREATE_COURSE_SQL);
    }
    public void insert(course.Course course) {
        springjdbcTemplate.update(INSERT_COURSE_SQL, course.getCourseId(), course.getTitle(), course.getInstructor(), course.getCategory(), course.getStartAt(), course.getEndAt(), course.getCapacity(), course.getEnrolled(), course.getStatus());
    }
    // public void delete(int courseId) {
    //     springjdbcTemplate.update(DELETE_COURSE_SQL, courseId);
    // }
}