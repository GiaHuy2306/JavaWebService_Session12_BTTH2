package com.btth2.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Course {
    // Getter và Setter
    private Long id;
    private String courseName;
    private String instructor;
    private Integer durationHours;
    private Double fee;

    public Course() {}

    public Course(Long id, String courseName, String instructor, Integer durationHours, Double fee) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.durationHours = durationHours;
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", courseName='" + courseName + '\'' + '}';
    }
}