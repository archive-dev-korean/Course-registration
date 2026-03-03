package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity //table과과 클래스 명이 같으면 table 명 생략 가능
@Getter
public class Course {

    @Id
    private int courseId;
    //마찬가지로 column 명이 변수 명과 같으면 column 명 생략 가능
    // @Column(name = "title") 

    private String title;
    private String instructor;
    private String category;
    private String startAt;
    private String endAt;
    private int capacity;
    private int enrolledcount;
    private String status;
    private int version;

    public Course() {
    }

    //Constructor
    public Course(int courseId, String title, String instructor, String category,String startAt, String endAt, int capacity, int enrolledcount, String status, int version) 
    {
        this.courseId = courseId;
        this.title = title;
        this.instructor = instructor;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
        this.capacity = capacity;
        this.enrolledcount = enrolledcount;
        this.status = status;
        this.version = version;
    }

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
                ", status='" + status + '\'' +
                ", enrolledcount=" + enrolledcount +
                ", version=" + version +
                '}';
    }
}

