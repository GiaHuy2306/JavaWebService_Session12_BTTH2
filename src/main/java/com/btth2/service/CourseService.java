package com.btth2.service;

import com.btth2.model.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CourseService {
    // Khởi tạo Logger cho tầng Service
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    private final List<Course> courses = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public CourseService() {
        courses.add(new Course(idCounter.getAndIncrement(), "Java Web với Spring Boot", "Nguyễn Công Gia Huy", 60, 2500000.0));
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public Optional<Course> getCourseById(Long id) {
        Optional<Course> course = courses.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (!course.isPresent()) {
            // YÊU CẦU: WARN - Log khi không tìm thấy Course theo ID
            logger.warn("[WARN] Không tìm thấy khóa học nào có ID: {}", id);
        }
        return course;
    }

    public Course addCourse(Course course) {
        course.setId(idCounter.getAndIncrement());
        courses.add(course);
        // YÊU CẦU: INFO - Log khi tạo mới thành công
        logger.info("[INFO] Thêm mới khóa học thành công: {}", course.getCourseName());
        return course;
    }

    public Optional<Course> updateCourse(Long id, Course updatedCourse) {
        Optional<Course> existingCourseOpt = courses.stream().filter(c -> c.getId().equals(id)).findFirst();

        if (existingCourseOpt.isPresent()) {
            Course existingCourse = existingCourseOpt.get();
            existingCourse.setCourseName(updatedCourse.getCourseName());
            existingCourse.setInstructor(updatedCourse.getInstructor());
            existingCourse.setDurationHours(updatedCourse.getDurationHours());
            existingCourse.setFee(updatedCourse.getFee());
            // YÊU CẦU: INFO - Log khi cập nhật thành công
            logger.info("[INFO] Cập nhật thành công khóa học ID: {}", id);
            return Optional.of(existingCourse);
        } else {
            // YÊU CẦU: WARN - Log khi cập nhật thất bại do sai ID
            logger.warn("[WARN] Thất bại khi cập nhật! Không tồn tại khóa học ID: {}", id);
            return Optional.empty();
        }
    }

    public boolean deleteCourse(Long id) {
        boolean removed = courses.removeIf(c -> c.getId().equals(id));
        if (!removed) {
            logger.warn("[WARN] Thất bại khi xóa! Không tồn tại khóa học ID: {}", id);
        }
        return removed;
    }
}
