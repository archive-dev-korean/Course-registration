package course;
public class Course {
    private int courseId;
    private String title;
    private String instructor;
    private String category;
    private String startAt;
    private String endAt;
    private int capacity;
    private int enrolled;
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