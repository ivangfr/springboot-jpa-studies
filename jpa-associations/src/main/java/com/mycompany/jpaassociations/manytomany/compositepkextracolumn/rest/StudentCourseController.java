package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest;

import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.StudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service.CourseService;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service.CourseStudentService;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.service.StudentService;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper.CourseMapper;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper.CourseStudentMapper;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class StudentCourseController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final CourseStudentService courseStudentService;
    private final StudentMapper studentMapper;
    private final CourseMapper courseMapper;
    private final CourseStudentMapper courseStudentMapper;

    // -------
    // Student

    @GetMapping("/students/{studentId}")
    public StudentDto getStudent(@PathVariable Long studentId) {
        Student student = studentService.validateAndGetStudent(studentId);
        return studentMapper.toStudentDto(student);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/students")
    public StudentDto createStudent(@Valid @RequestBody CreateStudentDto createStudentDto) {
        Student student = studentMapper.toStudent(createStudentDto);
        student = studentService.saveStudent(student);
        return studentMapper.toStudentDto(student);
    }

    @PutMapping("/students/{studentId}")
    public StudentDto updateStudent(@PathVariable Long studentId, @Valid @RequestBody UpdateStudentDto updateStudentDto) {
        Student student = studentService.validateAndGetStudent(studentId);
        studentMapper.updateStudentFromDto(updateStudentDto, student);
        student = studentService.saveStudent(student);
        return studentMapper.toStudentDto(student);
    }

    @DeleteMapping("/students/{studentId}")
    public StudentDto deleteStudent(@PathVariable Long studentId) {
        Student student = studentService.validateAndGetStudent(studentId);
        studentService.deleteStudent(student);
        return studentMapper.toStudentDto(student);
    }

    // ------
    // Course

    @GetMapping("/courses/{courseId}")
    public CourseDto getCourse(@PathVariable Long courseId) {
        Course course = courseService.validateAndGetCourse(courseId);
        return courseMapper.toCourseDto(course);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses")
    public CourseDto createCourse(@Valid @RequestBody CreateCourseDto createCourseDto) {
        Course course = courseMapper.toCourse(createCourseDto);
        course = courseService.saveCourse(course);
        return courseMapper.toCourseDto(course);
    }

    @PutMapping("/courses/{courseId}")
    public CourseDto updateCourse(@PathVariable Long courseId, @Valid @RequestBody UpdateCourseDto updateCourseDto) {
        Course course = courseService.validateAndGetCourse(courseId);
        courseMapper.updateCourseFromDto(updateCourseDto, course);
        course = courseService.saveCourse(course);
        return courseMapper.toCourseDto(course);
    }

    @DeleteMapping("/courses/{courseId}")
    public CourseDto deleteCourse(@PathVariable Long courseId) {
        Course course = courseService.validateAndGetCourse(courseId);
        courseService.deleteCourse(course);
        return courseMapper.toCourseDto(course);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/courses/{courseId}/students/{studentId}")
    public CourseStudentDto enrollStudentInCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        Course course = courseService.validateAndGetCourse(courseId);
        Student student = studentService.validateAndGetStudent(studentId);

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setStudent(student);
        courseStudent.setCourse(course);
        courseStudent = courseStudentService.saveCourseStudent(courseStudent);

        return courseStudentMapper.toCourseStudentDto(courseStudent);
    }

    @DeleteMapping("/courses/{courseId}/students/{studentId}")
    public CourseStudentDto unregisterStudentOfCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        CourseStudent courseStudent = courseStudentService.validateAndGetCourseStudent(courseId, studentId);
        courseStudentService.deleteCourseStudent(courseStudent);
        return courseStudentMapper.toCourseStudentDto(courseStudent);
    }

    @PutMapping("/courses/{courseId}/students/{studentId}")
    public CourseStudentDto updateStudentDataInCourse(@PathVariable Long courseId, @PathVariable Long studentId,
                                                      @Valid @RequestBody UpdateCourseStudentDto updateCourseStudentDto) {
        CourseStudent courseStudent = courseStudentService.validateAndGetCourseStudent(courseId, studentId);
        courseStudentMapper.updateCourseStudentFromDto(updateCourseStudentDto, courseStudent);
        courseStudentService.saveCourseStudent(courseStudent);
        return courseStudentMapper.toCourseStudentDto(courseStudent);
    }

}
