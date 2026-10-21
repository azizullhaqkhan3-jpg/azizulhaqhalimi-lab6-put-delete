
package com.Coursehub.Course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin(origins = "http://localhost:5173")
public class CourseController {

    public record Course(long id, String code, String title, int credits) {}

    private final List<Course> courses = new ArrayList<>(List.of(
        new Course(1, "EWA301", "Enterprise Web App", 3),
        new Course(2, "DB201", "Database Systems", 3),
        new Course(3, "SE202", "Software Engineering", 3)
    ));

    @GetMapping
    public List<Course> getCourses() {
        return courses;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable long id,
            @RequestBody Course updatedCourse) {

        if (updatedCourse.code() == null
                || updatedCourse.title() == null
                || updatedCourse.code().isBlank()
                || updatedCourse.title().isBlank()
                || updatedCourse.credits() < 1) {
            return ResponseEntity.badRequest().build();
        }

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).id() == id) {

                Course updated = new Course(
                    id,
                    updatedCourse.code().trim(),
                    updatedCourse.title().trim(),
                    updatedCourse.credits()
                );

                courses.set(i, updated);
                return ResponseEntity.ok(updated);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable long id) {
        boolean removed =
            courses.removeIf(course -> course.id() == id);

        if (removed) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
