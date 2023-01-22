package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidCourseIdException;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.CourseRepository;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService, UserDetailsService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Student> listAll() {
        return this.studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return this.studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
        List<Course> courses = this.courseRepository.findAllById(courseId);
        String encryptedPassword = this.passwordEncoder.encode(password);
        Student student = new Student(name, email, encryptedPassword, type, courses, enrollmentDate);
        return this.studentRepository.save(student);
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        Student student = this.studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(password);
        student.setType(type);
        List<Course> courses = this.courseRepository.findAllById(coursesId);
        student.setCourses(courses);
        student.setEnrollmentDate(enrollmentDate);
        return this.studentRepository.save(student);
    }

    @Override
    public Student delete(Long id) {
        Student student = this.studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        this.studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        if(courseId != null && yearsOfStudying != null) {
            return this.studentRepository.findAllByCoursesContainingAndEnrollmentDateBefore(
                    this.courseRepository.findById(courseId).orElseThrow(InvalidCourseIdException::new),
                    LocalDate.now().minusYears(yearsOfStudying));
        } else if(courseId != null) {
            return this.studentRepository.findAllByCoursesContaining(
                    this.courseRepository.findById(courseId).orElseThrow(InvalidCourseIdException::new));
        } else if(yearsOfStudying != null) {
            return this.studentRepository.findAllByEnrollmentDateBefore(LocalDate.now().minusYears(yearsOfStudying));
        } else {
            return this.studentRepository.findAll();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student =  this.studentRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new User(student.getEmail(), student.getPassword(),
                Stream.of(new SimpleGrantedAuthority("ROLE_"+student.getType().toString())).collect(Collectors.toList()));
    }
}
