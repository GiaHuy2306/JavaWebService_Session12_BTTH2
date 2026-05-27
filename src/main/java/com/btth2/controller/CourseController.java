package com.btth2.controller;

import com.btth2.model.Course;
import com.btth2.service.CourseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    // Khởi tạo Logger cho tầng Controller
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        // YÊU CẦU: INFO - Log khi có request đến (method + endpoint)
        logger.info("[INFO] Request tiếp nhận: GET -> /api/courses");
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        logger.info("[INFO] Request tiếp nhận: GET -> /api/courses/{}", id);
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody Course course) {
        logger.info("[INFO] Request tiếp nhận: POST -> /api/courses");
        Course savedCourse = courseService.addCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {
        logger.info("[INFO] Request tiếp nhận: PUT -> /api/courses/{}", id);
        return courseService.updateCourse(id, course)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("[INFO] Request tiếp nhận: DELETE -> /api/courses/{}", id);
        if (courseService.deleteCourse(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // API phụ để test trường hợp sinh lỗi hệ thống nghiêm trọng (Runtime Exception)
    @GetMapping("/test-error")
    public ResponseEntity<String> triggerError() {
        logger.info("[INFO] Request tiếp nhận: GET -> /api/courses/test-error");
        try {
            // Cố tình tạo ra một lỗi chia cho 0 để kích hoạt khối catch
            int result = 10 / 0;
            return ResponseEntity.ok("Thành công");
        } catch (RuntimeException e) {
            // YÊU CẦU: ERROR - Log khi bắt được RuntimeException
            logger.error("[ERROR] Đã xảy ra lỗi nghiêm trọng hệ thống: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi máy chủ!");
        }
    }
}