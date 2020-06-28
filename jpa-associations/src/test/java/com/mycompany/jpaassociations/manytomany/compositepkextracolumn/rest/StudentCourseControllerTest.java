package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class StudentCourseControllerTest extends AbstractTestcontainers {

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
        Student student = studentRepository.save(getDefaultStudent());

        String url = String.format(API_STUDENTS_STUDENT_ID_URL, student.getId());
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
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.postForEntity(API_STUDENTS_URL, createStudentDto, StudentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createStudentDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getCourses().size());

        Optional<Student> studentOptional = studentRepository.findById(responseEntity.getBody().getId());
        assertTrue(studentOptional.isPresent());
        studentOptional.ifPresent(s -> assertEquals(createStudentDto.getName(), s.getName()));
    }

    @Test
    void testUpdateStudent() {
        Student student = studentRepository.save(getDefaultStudent());
        UpdateStudentDto updateStudentDto = getDefaultUpdateStudentDto();

        HttpEntity<UpdateStudentDto> requestUpdate = new HttpEntity<>(updateStudentDto);
        String url = String.format(API_STUDENTS_STUDENT_ID_URL, student.getId());
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, StudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateStudentDto.getName(), responseEntity.getBody().getName());

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertTrue(studentOptional.isPresent());
        studentOptional.ifPresent(s -> assertEquals(updateStudentDto.getName(), s.getName()));
    }

    @Test
    void testDeleteStudent() {
        Student student = studentRepository.save(getDefaultStudent());

        String url = String.format(API_STUDENTS_STUDENT_ID_URL, student.getId());
        ResponseEntity<StudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, StudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(student.getId(), responseEntity.getBody().getId());
        assertEquals(student.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getCourses().size());

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertFalse(studentOptional.isPresent());
    }

    @Test
    void testGetCourse() {
        Course course = courseRepository.save(getDefaultCourse());

        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
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
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.postForEntity(API_COURSES_URL, createCourseDto, CourseDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getId());
        assertEquals(createCourseDto.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getStudents().size());

        Optional<Course> courseOptional = courseRepository.findById(responseEntity.getBody().getId());
        assertTrue(courseOptional.isPresent());
        courseOptional.ifPresent(c -> assertEquals(createCourseDto.getName(), c.getName()));
    }

    @Test
    void testUpdateCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        UpdateCourseDto updateCourseDto = getDefaultUpdateCourseDto();

        HttpEntity<UpdateCourseDto> requestUpdate = new HttpEntity<>(updateCourseDto);
        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestUpdate, CourseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updateCourseDto.getName(), responseEntity.getBody().getName());

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertTrue(courseOptional.isPresent());
        courseOptional.ifPresent(c -> assertEquals(updateCourseDto.getName(), c.getName()));
    }

    @Test
    void testDeleteCourse() {
        Course course = courseRepository.save(getDefaultCourse());

        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
        ResponseEntity<CourseDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CourseDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getId());
        assertEquals(course.getName(), responseEntity.getBody().getName());
        assertEquals(0, responseEntity.getBody().getStudents().size());

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertFalse(courseOptional.isPresent());
    }

    @Test
    void testEnrollStudentInCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        Student student = studentRepository.save(getDefaultStudent());

        String url = String.format(API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL, course.getId(), student.getId());
        ResponseEntity<CourseStudentDto> responseEntity = testRestTemplate.postForEntity(url, null, CourseStudentDto.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getCourse().getId());
        assertEquals(course.getName(), responseEntity.getBody().getCourse().getName());
        assertEquals(student.getId(), responseEntity.getBody().getStudent().getId());
        assertEquals(student.getName(), responseEntity.getBody().getStudent().getName());
        assertNotNull(responseEntity.getBody().getRegistrationDate());
        assertNull(responseEntity.getBody().getGrade());

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertTrue(courseOptional.isPresent());
        courseOptional.ifPresent(c -> assertEquals(1, c.getStudents().size()));

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertTrue(studentOptional.isPresent());
        studentOptional.ifPresent(s -> assertEquals(1, s.getCourses().size()));

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertTrue(courseStudentOptional.isPresent());
        courseStudentOptional.ifPresent(cs -> {
            assertEquals(student, cs.getStudent());
            assertEquals(course, cs.getCourse());
            assertNotNull(cs.getRegistrationDate());
        });
    }

    @Test
    void testUnregisterStudentOfCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        Student student = studentRepository.save(getDefaultStudent());

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentRepository.save(courseStudent);

        String url = String.format(API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL, course.getId(), student.getId());
        ResponseEntity<CourseStudentDto> responseEntity = testRestTemplate.exchange(url, HttpMethod.DELETE, null, CourseStudentDto.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(course.getId(), responseEntity.getBody().getCourse().getId());
        assertEquals(course.getName(), responseEntity.getBody().getCourse().getName());
        assertEquals(student.getId(), responseEntity.getBody().getStudent().getId());
        assertEquals(student.getName(), responseEntity.getBody().getStudent().getName());
        assertNotNull(responseEntity.getBody().getRegistrationDate());

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertTrue(courseOptional.isPresent());
        courseOptional.ifPresent(c -> assertEquals(0, c.getStudents().size()));

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertTrue(studentOptional.isPresent());
        studentOptional.ifPresent(s -> assertEquals(0, s.getCourses().size()));

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertFalse(courseStudentOptional.isPresent());
    }

    @Test
    void testUpdateStudentDataInCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        Student student = studentRepository.save(getDefaultStudent());

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentRepository.save(courseStudent);

        UpdateCourseStudentDto updateCourseStudentDto = getDefaultUpdateCourseStudentDto();

        HttpEntity<UpdateCourseStudentDto> requestUpdate = new HttpEntity<>(updateCourseStudentDto);
        String url = String.format(API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL, course.getId(), student.getId());
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
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertTrue(courseStudentOptional.isPresent());
        courseStudentOptional.ifPresent(cs -> {
            assertEquals(student, cs.getStudent());
            assertEquals(course, cs.getCourse());
            assertNotNull(cs.getRegistrationDate());
            assertEquals(updateCourseStudentDto.getGrade(), cs.getGrade());
        });
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

    private static final String API_STUDENTS_URL = "/api/students";
    private static final String API_STUDENTS_STUDENT_ID_URL = "/api/students/%s";
    private static final String API_COURSES_URL = "/api/courses";
    private static final String API_COURSES_COURSE_ID_URL = "/api/courses/%s";
    private static final String API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL = "/api/courses/%s/students/%s";

}