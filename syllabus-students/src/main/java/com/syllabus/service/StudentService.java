package com.syllabus.service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.client.account.AccountClient;
import com.syllabus.client.account.AccountResponse;
import com.syllabus.client.settings.CourseResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.UniversityCoursesResponse;
import com.syllabus.client.settings.UniversityResponse;
import com.syllabus.data.model.StudentModel;
import com.syllabus.exception.CourseNotFoundException;
import com.syllabus.exception.ProgramNotFoundException;
import com.syllabus.exception.UniversityInfoInvalidException;
import com.syllabus.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final AccountClient accountClient;
    private final SettingsClient configClient;

    public StudentModel createStudent(StudentModel student) {

        validateUniversityInfo(student.getUniversityCode(), Short.valueOf(student.getTerm().toString()),
                student.getProgramCode().toString());

        if (!validCourses(student.getCourseCodes(), student.getUniversityCode()))
            throw new CourseNotFoundException("course not found");

        this.updateInstantStudent(student, Instant.now(), false);
        student.setEmail(this.getUser().getEmail());

        return studentRepository.save(student);
    }

    public StudentModel getStudent(String id) {
        return studentRepository.findByStudentIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CourseNotFoundException("student not found"));
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

    private AccountResponse getUser() {
        return this.accountClient.getAccount().getUser();
    }

    private void updateInstantStudent(StudentModel student, Instant instant, boolean delete) {

        student.setUpdatedAt(Instant.now());

        if (!delete)
            student.setCreatedAt(instant);
        else
            student.setDeletedAt(instant);

    }

    private boolean validCourses(Set<String> courses, String universityCode) {
        UniversityCoursesResponse universityCourses = configClient.getCoursesByUniversity(universityCode);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);

    }

    private void validateUniversityInfo(String universityCode, Short term, String programCode) {
        if (!validUniversityCode(universityCode))
            throw new UniversityInfoInvalidException("university code invalid");

        try {
            Short programTerms = configClient.getProgramByIdOrCode(programCode).getTerms();
                if (!validTerm(term, programTerms))
                    throw new UniversityInfoInvalidException("term invalid");
        } catch (Exception ex) {
            throw new ProgramNotFoundException("program not found");
        }

    }

    private boolean validUniversityCode(String universityCode) {
        UniversityResponse university = configClient.getUniversityByIdOrCode(universityCode);
        System.out.println(university != null);

        return (university != null);
    }

    private boolean validTerm(Short term, Short programTerm) {
        System.out.println("term :" + term);
        System.out.println("program term: " + programTerm);

        return (term > 1 && term <= programTerm);
    }

}
