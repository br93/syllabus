package com.syllabus.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.syllabus.data.model.StudentModel;
import com.syllabus.exception.CourseNotFoundException;
import com.syllabus.exception.StudentNotFoundException;
import com.syllabus.exception.UserUnauthorizedException;
import com.syllabus.message.MessageConstants;
import com.syllabus.repository.StudentRepository;
import com.syllabus.util.UserValidation;
import com.syllabus.util.Validator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserValidation userValidation;
    private final Validator validator;

    public StudentModel createStudent(StudentModel student) {

        validator.validateUniversityInfo(student.getUniversityCode(), Short.valueOf(student.getTerm().toString()),
                student.getProgramCode().toString());

        if (!validator.validCourses(student.getCourseCodes(), student.getUniversityCode()))
            throw new CourseNotFoundException(MessageConstants.COURSE_NOT_FOUND);

        this.updateInstantStudent(student, Instant.now(), false);
        student.setUserId(userValidation.getUser().getUserId());

        return studentRepository.save(student);
    }

    public StudentModel getStudent(String id) {

        var student = studentRepository.findByStudentIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new StudentNotFoundException(MessageConstants.STUDENT_NOT_FOUND));

        if (!userValidation.isAuthorizedById(student.getUserId()))
            throw new UserUnauthorizedException(MessageConstants.USER_UNAUTHORIZED);

        return student;

    }

    public StudentModel getStudentByUserId(String userId) {

        if (!userValidation.isAuthorizedById(userId))
            throw new UserUnauthorizedException(MessageConstants.USER_UNAUTHORIZED);

        return studentRepository.findByUserIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new StudentNotFoundException(MessageConstants.STUDENT_NOT_FOUND));

    }

    public StudentModel updateStudent(String id, StudentModel novoStudent) {

        StudentModel student = this.getStudent(id);
        novoStudent.setStudentId(student.getStudentId());
        this.updateInstantStudent(novoStudent, student.getCreatedAt(), false);

        return studentRepository.save(novoStudent);
    }

    public void deleteStudent(String id) {
        StudentModel student = this.getStudent(id);
        this.updateInstantStudent(student, Instant.now(), true);

        studentRepository.save(student);
    }

    private void updateInstantStudent(StudentModel student, Instant instant, boolean delete) {

        student.setUpdatedAt(Instant.now());

        if (!delete)
            student.setCreatedAt(instant);
        else
            student.setDeletedAt(instant);

    }

}
