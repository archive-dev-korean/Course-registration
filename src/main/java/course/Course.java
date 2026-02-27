package course;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
//Spring JPA 활용한 Entity 연동
@Entity //table과과 클래스 명이 같으면 table 명 생략 가능
public class Course {
    @Id
    private int courseId;
//마찬가지로 column 명이 변수 명과 같으면 column 명 생략 가능

    // @Column(name = "title") 
    private String title;
    // @Column(name = "instructor")
    private String instructor;
    // @Column(name = "category")      
    private String category;
    // @Column(name = "startAt")
    private String startAt;
    // @Column(name = "endAt")
    private String endAt;
    // @Column(name = "capacity")
    private int capacity;
    // @Column(name = "enrolled")
    private int enrolled;
    // @Column(name = "status")    //마찬가지로 column 명이 변수수 명과 같으면 column 명 생략 가능
    private String status;
    public Course() {
    }

    //Constructor
    public Course(int courseId, String title, String instructor, String category, String startAt, String endAt, int capacity, int enrolled, String status) {
        this.courseId = courseId;
        this.title = title;
        this.instructor = instructor;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
        this.capacity = capacity;
        this.enrolled = enrolled;
        this.status = status;
    }
    //Getters
    public int getCourseId() {
        return courseId;
    }
    public String getTitle() {
        return title;
    }
    public String getInstructor() {
        return instructor;
    }
    public String getCategory() {
        return category;
    }
    public String getStartAt() {
        return startAt;
    }
    public String getEndAt() {
        return endAt;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getEnrolled() {
        return enrolled;
    }
    public String getStatus() {
        return status;
    }
    //toString
    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", instructor='" + instructor + '\'' +
                ", category='" + category + '\'' +
                ", startAt='" + startAt + '\'' +
                ", endAt='" + endAt + '\'' +
                ", capacity=" + capacity +
                ", enrolled=" + enrolled +
                ", status='" + status + '\'' +
                '}';
    }
}   