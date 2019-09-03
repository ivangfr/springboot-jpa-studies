package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest;

import com.mycompany.jpaassociations.ContainersExtension;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.CourseRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.CourseStudentRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.StudentRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.StudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseStudentDto;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateStudentDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({SpringExtension.class, ContainersExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class StudentCourseControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseStudentRepository courseStudentRepository;

    @Test
    void testGetStudent() {
        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        String url = String.format("/api/students/%s", student.getId());
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.getForEntity(url, StudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(student.getId(), responseEntity.getBody().getId());
        assertEquals(student.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getCourses().size());
    }

    @Test
    void testCreateStudent() {
        CreateStudentDto createStudentDto = getDefaultCreateStudentDto();
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.postForEntity("/api/students", createStudentDto, StudentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createStudentDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getCourses().size());

        Optional<Student> optionalStudent = studentRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalStudent.isPresent());
        assertEquals(createStudentDto.getName(), optionalStudent.get().getName());
    }

    @Test
    void testUpdateStudent() {
        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        UpdateStudentDto updateStudentDto = getDefaultUpdateStudentDto();

        HttpEntity<UpdateStudentDto> requestUpdate = new HttpEntity<>(updateStudentDto);
        String url = String.format("/api/students/%s", student.getId());
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, StudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateStudentDto.getName(), responseEntity.getBody().getName());

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        assertTrue(optionalStudent.isPresent());
        assertEquals(updateStudentDto.getName(), optionalStudent.get().getName());
    }

    @Test
    void testDeleteStudent() {
        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        String url = String.format("/api/students/%s", student.getId());
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, StudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(student.getId(), responseEntity.getBody().getId());
        assertEquals(student.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getCourses().size());

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        assertFalse(optionalStudent.isPresent());
    }

    @Test
    void testGetCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        String url = String.format("/api/courses/%s", course.getId());
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.getForEntity(url, CourseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getId());
        assertEquals(course.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getStudents().size());
    }

    @Test
    void testCreateCourse() {
        CreateCourseDto createCourseDto = getDefaultCreateCourseDto();
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.postForEntity("/api/courses", createCourseDto, CourseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createCourseDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getStudents().size());

        Optional<Course> optionalCourse = courseRepository.findById(responseEntity.getBody().getId());
        assertTrue(optionalCourse.isPresent());
        assertEquals(createCourseDto.getName(), optionalCourse.get().getName());
    }

    @Test
    void testUpdateCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        UpdateCourseDto updateCourseDto = getDefaultUpdateCourseDto();

        HttpEntity<UpdateCourseDto> requestUpdate = new HttpEntity<>(updateCourseDto);
        String url = String.format("/api/courses/%s", course.getId());
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, CourseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateCourseDto.getName(), responseEntity.getBody().getName());

        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        assertTrue(optionalCourse.isPresent());
        assertEquals(updateCourseDto.getName(), optionalCourse.get().getName());
    }

    @Test
    void testDeleteCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        String url = String.format("/api/courses/%s", course.getId());
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CourseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getId());
        assertEquals(course.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getStudents().size());

        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        assertFalse(optionalCourse.isPresent());
    }

    @Test
    void testEnrollStudentInCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        String url = String.format("/api/courses/%s/students/%s", course.getId(), student.getId());
        ResponseEntity<CourseStudentDto> responseEntity = testRestTemplate.postForEntity(url, null, CourseStudentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getCourse().getId());
        assertEquals(course.getName(), responseEntity.getBody().getCourse().getName());
        assertEquals(student.getId(), responseEntity.getBody().getStudent().getId());
        assertEquals(student.getName(), responseEntity.getBody().getStudent().getName());
        assertNotNull(responseEntity.getBody().getRegistrationDate());
        assertNull(responseEntity.getBody().getGrade());

        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        assertTrue(optionalCourse.isPresent());
        assertEquals(1, optionalCourse.get().getStudents().size());

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        assertTrue(optionalStudent.isPresent());
        assertEquals(1, optionalStudent.get().getCourses().size());

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> optionalCourseStudent = courseStudentRepository.findById(courseStudentPk);
        assertTrue(optionalCourseStudent.isPresent());
        assertEquals(student, optionalCourseStudent.get().getStudent());
        assertEquals(course, optionalCourseStudent.get().getCourse());
        assertNotNull(optionalCourseStudent.get().getRegistrationDate());
    }

    @Test
    void testUnregisterStudentOfCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentRepository.save(courseStudent);

        String url = String.format("/api/courses/%s/students/%s", course.getId(), student.getId());
        ResponseEntity<CourseStudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CourseStudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getCourse().getId());
        assertEquals(course.getName(), responseEntity.getBody().getCourse().getName());
        assertEquals(student.getId(), responseEntity.getBody().getStudent().getId());
        assertEquals(student.getName(), responseEntity.getBody().getStudent().getName());
        assertNotNull(responseEntity.getBody().getRegistrationDate());

        Optional<Course> optionalCourse = courseRepository.findById(course.getId());
        assertTrue(optionalCourse.isPresent());
        assertEquals(0, optionalCourse.get().getStudents().size());

        Optional<Student> optionalStudent = studentRepository.findById(student.getId());
        assertTrue(optionalStudent.isPresent());
        assertEquals(0, optionalStudent.get().getCourses().size());

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> optionalCourseStudent = courseStudentRepository.findById(courseStudentPk);
        assertFalse(optionalCourseStudent.isPresent());
    }

    @Test
    void testUpdateStudentDataInCourse() {
        Course course = getDefaultCourse();
        course = courseRepository.save(course);

        Student student = getDefaultStudent();
        student = studentRepository.save(student);

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentRepository.save(courseStudent);

        UpdateCourseStudentDto updateCourseStudentDto = getDefaultUpdateCourseStudentDto();

        HttpEntity<UpdateCourseStudentDto> requestUpdate = new HttpEntity<>(updateCourseStudentDto);
        String url = String.format("/api/courses/%s/students/%s", course.getId(), student.getId());
        ResponseEntity<CourseStudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, CourseStudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getCourse().getId());
        assertEquals(course.getName(), responseEntity.getBody().getCourse().getName());
        assertEquals(student.getId(), responseEntity.getBody().getStudent().getId());
        assertEquals(student.getName(), responseEntity.getBody().getStudent().getName());
        assertNotNull(responseEntity.getBody().getRegistrationDate());
        assertEquals(updateCourseStudentDto.getGrade(), responseEntity.getBody().getGrade());

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> optionalCourseStudent = courseStudentRepository.findById(courseStudentPk);
        assertTrue(optionalCourseStudent.isPresent());
        assertEquals(student, optionalCourseStudent.get().getStudent());
        assertEquals(course, optionalCourseStudent.get().getCourse());
        assertNotNull(optionalCourseStudent.get().getRegistrationDate());
        assertEquals(updateCourseStudentDto.getGrade(), optionalCourseStudent.get().getGrade());
    }

    private Student getDefaultStudent() {
        Student student = new Student();
        student.setName("Ivan Franchin");
        return student;
    }

    private CreateStudentDto getDefaultCreateStudentDto() {
        CreateStudentDto createStudentDto = new CreateStudentDto();
        createStudentDto.setName("Ivan Franchin");
        return createStudentDto;
    }

    private UpdateStudentDto getDefaultUpdateStudentDto() {
        UpdateStudentDto updateStudentDto = new UpdateStudentDto();
        updateStudentDto.setName("Steve Jobs");
        return updateStudentDto;
    }

    private Course getDefaultCourse() {
        Course course = new Course();
        course.setName("Java 8");
        return course;
    }

    private CreateCourseDto getDefaultCreateCourseDto() {
        CreateCourseDto createCourseDto = new CreateCourseDto();
        createCourseDto.setName("Java 8");
        return createCourseDto;
    }

    private UpdateCourseDto getDefaultUpdateCourseDto() {
        UpdateCourseDto updateCourseDto = new UpdateCourseDto();
        updateCourseDto.setName("Springboot");
        return updateCourseDto;
    }

    private UpdateCourseStudentDto getDefaultUpdateCourseStudentDto() {
        UpdateCourseStudentDto updateCourseStudentDto = new UpdateCourseStudentDto();
        updateCourseStudentDto.setGrade((short) 8);
        return updateCourseStudentDto;
    }

}