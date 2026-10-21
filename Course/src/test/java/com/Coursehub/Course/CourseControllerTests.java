package com.Coursehub.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class CourseControllerTests {

    private final CourseController controller = new CourseController();

    @Test
    void returnsAtLeastThreeCoursesWithRequiredFields() {
        var courses = controller.getCourses();

        assertEquals(3, courses.size());
        assertFalse(courses.stream().anyMatch(course ->
                course.code() == null || course.title() == null || course.credits() < 1));
    }
}
