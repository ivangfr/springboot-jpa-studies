package com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest;

import com.mycompany.jpaassociations.AbstractTestcontainers;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Course;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudent;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.CourseStudentPk;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.model.Student;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.CourseRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.CourseStudentRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.repository.StudentRepository;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseResponse;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CourseStudentResponse;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateCourseRequest;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.CreateStudentRequest;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.StudentResponse;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseRequest;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateCourseStudentRequest;
import com.mycompany.jpaassociations.manytomany.compositepkextracolumn.rest.dto.UpdateStudentRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        ResponseEntity<StudentResponse> responseEntity = testRestTemplate.getForEntity(url, StudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(student.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(student.getName());
        assertThat(responseEntity.getBody().getCourses().size()).isEqualTo(0);
    }

    @Test
    void testCreateStudent() {
        CreateStudentRequest createStudentRequest = new CreateStudentRequest("Ivan Franchin");
        ResponseEntity<StudentResponse> responseEntity = testRestTemplate.postForEntity(
                API_STUDENTS_URL, createStudentRequest, StudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createStudentRequest.getName());
        assertThat(responseEntity.getBody().getCourses().size()).isEqualTo(0);

        Optional<Student> studentOptional = studentRepository.findById(responseEntity.getBody().getId());
        assertThat(studentOptional.isPresent()).isTrue();
        studentOptional.ifPresent(s -> assertThat(s.getName()).isEqualTo(createStudentRequest.getName()));
    }

    @Test
    void testUpdateStudent() {
        Student student = studentRepository.save(getDefaultStudent());
        UpdateStudentRequest updateStudentRequest = new UpdateStudentRequest("Steve Jobs");

        HttpEntity<UpdateStudentRequest> requestUpdate = new HttpEntity<>(updateStudentRequest);
        String url = String.format(API_STUDENTS_STUDENT_ID_URL, student.getId());
        ResponseEntity<StudentResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, StudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updateStudentRequest.getName());

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertThat(studentOptional.isPresent()).isTrue();
        studentOptional.ifPresent(s -> assertThat(s.getName()).isEqualTo(updateStudentRequest.getName()));
    }

    @Test
    void testDeleteStudent() {
        Student student = studentRepository.save(getDefaultStudent());

        String url = String.format(API_STUDENTS_STUDENT_ID_URL, student.getId());
        ResponseEntity<StudentResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, StudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(student.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(student.getName());
        assertThat(responseEntity.getBody().getCourses().size()).isEqualTo(0);

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertThat(studentOptional.isPresent()).isFalse();
    }

    @Test
    void testGetCourse() {
        Course course = courseRepository.save(getDefaultCourse());

        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
        ResponseEntity<CourseResponse> responseEntity = testRestTemplate.getForEntity(url, CourseResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(course.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(course.getName());
        assertThat(responseEntity.getBody().getStudents().size()).isEqualTo(0);
    }

    @Test
    void testCreateCourse() {
        CreateCourseRequest createCourseRequest = new CreateCourseRequest("Java 8");
        ResponseEntity<CourseResponse> responseEntity = testRestTemplate.postForEntity(
                API_COURSES_URL, createCourseRequest, CourseResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(createCourseRequest.getName());
        assertThat(responseEntity.getBody().getStudents().size()).isEqualTo(0);

        Optional<Course> courseOptional = courseRepository.findById(responseEntity.getBody().getId());
        assertThat(courseOptional.isPresent()).isTrue();
        courseOptional.ifPresent(c -> assertThat(c.getName()).isEqualTo(createCourseRequest.getName()));
    }

    @Test
    void testUpdateCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        UpdateCourseRequest updateCourseRequest = new UpdateCourseRequest("Springboot");

        HttpEntity<UpdateCourseRequest> requestUpdate = new HttpEntity<>(updateCourseRequest);
        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
        ResponseEntity<CourseResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, CourseResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getName()).isEqualTo(updateCourseRequest.getName());

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertThat(courseOptional.isPresent()).isTrue();
        courseOptional.ifPresent(c -> assertThat(c.getName()).isEqualTo(updateCourseRequest.getName()));
    }

    @Test
    void testDeleteCourse() {
        Course course = courseRepository.save(getDefaultCourse());

        String url = String.format(API_COURSES_COURSE_ID_URL, course.getId());
        ResponseEntity<CourseResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, CourseResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getId()).isEqualTo(course.getId());
        assertThat(responseEntity.getBody().getName()).isEqualTo(course.getName());
        assertThat(responseEntity.getBody().getStudents().size()).isEqualTo(0);

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertThat(courseOptional.isPresent()).isFalse();
    }

    @Test
    void testEnrollStudentInCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        Student student = studentRepository.save(getDefaultStudent());

        String url = String.format(API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL, course.getId(), student.getId());
        ResponseEntity<CourseStudentResponse> responseEntity = testRestTemplate.postForEntity(
                url, null, CourseStudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCourse().getId()).isEqualTo(course.getId());
        assertThat(responseEntity.getBody().getCourse().getName()).isEqualTo(course.getName());
        assertThat(responseEntity.getBody().getStudent().getId()).isEqualTo(student.getId());
        assertThat(responseEntity.getBody().getStudent().getName()).isEqualTo(student.getName());
        assertThat(responseEntity.getBody().getRegistrationDate()).isNotNull();
        assertThat(responseEntity.getBody().getGrade()).isNull();

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertThat(courseOptional.isPresent()).isTrue();
        courseOptional.ifPresent(c -> assertThat(c.getStudents().size()).isEqualTo(1));

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertThat(studentOptional.isPresent()).isTrue();
        studentOptional.ifPresent(s -> assertThat(s.getCourses().size()).isEqualTo(1));

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertThat(courseStudentOptional.isPresent()).isTrue();
        courseStudentOptional.ifPresent(cs -> {
            assertThat(cs.getStudent()).isEqualTo(student);
            assertThat(cs.getCourse()).isEqualTo(course);
            assertThat(cs.getRegistrationDate()).isNotNull();
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
        ResponseEntity<CourseStudentResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.DELETE, null, CourseStudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCourse().getId()).isEqualTo(course.getId());
        assertThat(responseEntity.getBody().getCourse().getName()).isEqualTo(course.getName());
        assertThat(responseEntity.getBody().getStudent().getId()).isEqualTo(student.getId());
        assertThat(responseEntity.getBody().getStudent().getName()).isEqualTo(student.getName());
        assertThat(responseEntity.getBody().getRegistrationDate()).isNotNull();

        Optional<Course> courseOptional = courseRepository.findById(course.getId());
        assertThat(courseOptional.isPresent()).isTrue();
        courseOptional.ifPresent(c -> assertThat(c.getStudents().size()).isEqualTo(0));

        Optional<Student> studentOptional = studentRepository.findById(student.getId());
        assertThat(studentOptional.isPresent()).isTrue();
        studentOptional.ifPresent(s -> assertThat(s.getCourses().size()).isEqualTo(0));

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertThat(courseStudentOptional.isPresent()).isFalse();
    }

    @Test
    void testUpdateStudentDataInCourse() {
        Course course = courseRepository.save(getDefaultCourse());
        Student student = studentRepository.save(getDefaultStudent());

        CourseStudent courseStudent = new CourseStudent();
        courseStudent.setCourse(course);
        courseStudent.setStudent(student);
        courseStudentRepository.save(courseStudent);

        UpdateCourseStudentRequest updateCourseStudentRequest = new UpdateCourseStudentRequest((short) 8);

        HttpEntity<UpdateCourseStudentRequest> requestUpdate = new HttpEntity<>(updateCourseStudentRequest);
        String url = String.format(API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL, course.getId(), student.getId());
        ResponseEntity<CourseStudentResponse> responseEntity = testRestTemplate.exchange(
                url, HttpMethod.PUT, requestUpdate, CourseStudentResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getCourse().getId()).isEqualTo(course.getId());
        assertThat(responseEntity.getBody().getCourse().getName()).isEqualTo(course.getName());
        assertThat(responseEntity.getBody().getStudent().getId()).isEqualTo(student.getId());
        assertThat(responseEntity.getBody().getStudent().getName()).isEqualTo(student.getName());
        assertThat(responseEntity.getBody().getRegistrationDate()).isNotNull();
        assertThat(responseEntity.getBody().getGrade()).isEqualTo(updateCourseStudentRequest.getGrade());

        CourseStudentPk courseStudentPk = new CourseStudentPk(course.getId(), student.getId());
        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findById(courseStudentPk);
        assertThat(courseStudentOptional.isPresent()).isTrue();
        courseStudentOptional.ifPresent(cs -> {
            assertThat(cs.getStudent()).isEqualTo(student);
            assertThat(cs.getCourse()).isEqualTo(course);
            assertThat(cs.getRegistrationDate()).isNotNull();
            assertThat(cs.getGrade()).isEqualTo(updateCourseStudentRequest.getGrade());
        });
    }

    private Student getDefaultStudent() {
        Student student = new Student();
        student.setName("Ivan Franchin");
        return student;
    }

    private Course getDefaultCourse() {
        Course course = new Course();
        course.setName("Java 8");
        return course;
    }

    private static final String API_STUDENTS_URL = "/api/students";
    private static final String API_STUDENTS_STUDENT_ID_URL = "/api/students/%s";
    private static final String API_COURSES_URL = "/api/courses";
    private static final String API_COURSES_COURSE_ID_URL = "/api/courses/%s";
    private static final String API_COURSES_COURSE_ID_STUDENTS_STUDENT_ID_URL = "/api/courses/%s/students/%s";
}