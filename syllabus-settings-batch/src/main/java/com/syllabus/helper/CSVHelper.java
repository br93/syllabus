package com.syllabus.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.syllabus.model.ClassModel;
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.model.CourseModel;
import com.syllabus.model.CourseProgramModel;
import com.syllabus.model.CourseTypeModel;
import com.syllabus.model.DayModel;
import com.syllabus.model.ProgramModel;
import com.syllabus.model.ScheduleModel;
import com.syllabus.repository.ClassRepository;
import com.syllabus.repository.CourseRepository;
import com.syllabus.repository.CourseTypeRepository;
import com.syllabus.repository.DayRepository;
import com.syllabus.repository.ProgramRepository;
import com.syllabus.repository.ScheduleRepository;
import com.syllabus.repository.UniversityRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CSVHelper {

	private final ProgramRepository programRepository;
	private final CourseRepository courseRepository;
	private final CourseTypeRepository courseTypeRepository;
	private final ClassRepository classRepository;
	private final DayRepository dayRepository;
	private final ScheduleRepository scheduleRepository;
	private final UniversityRepository universityRepository;

	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public List<ProgramModel> csvToProgram(InputStream inputStream) {
		List<ProgramModel> programs = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Name", "Code", "Terms", "University" };

		for (CSVRecord csvRecord : csvRecords) {

			ProgramModel program = new ProgramModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					csvRecord.get(headers[0]), csvRecord.get(headers[1]), 
					Short.valueOf(csvRecord.get(headers[2])), 
					universityRepository.findByUniversityCode(csvRecord.get(headers[3])));
			programs.add(program);

		}

		return programs;
	}

	public List<DayModel> csvToDay(InputStream inputStream) {
		List<DayModel> days = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Name", "Number" };

		for (CSVRecord csvRecord : csvRecords) {

			DayModel day = new DayModel(null, Instant.now(), Instant.now(), null, 
				UUID.randomUUID().toString(),
				csvRecord.get(headers[0]), Short.valueOf(csvRecord.get(headers[1])));
			days.add(day);

		}

		return days;
	}

	public List<CourseModel> csvToCourse(InputStream inputStream) {
		List<CourseModel> courses = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Name", "Code", "Workload", "University" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseModel course = new CourseModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), csvRecord.get(headers[0]), csvRecord.get(headers[1]),
					Short.valueOf(csvRecord.get(headers[2])), null, null,
					universityRepository.findByUniversityCode(csvRecord.get(headers[3])));
			
			courses.add(course);

		}

		return courses;
	}

	public List<CourseProgramModel> csvToCourseProgram(InputStream inputStream) {
		List<CourseProgramModel> coursePrograms = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Term", "Program", "Course", "Type" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseProgramModel courseProgram = new CourseProgramModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), 
					Short.valueOf(csvRecord.get(headers[0])),
					programRepository.findByProgramCode(csvRecord.get(headers[1])),
					courseRepository.findByCourseCode(csvRecord.get(headers[2])),
					courseTypeRepository.findByTypeName(csvRecord.get(headers[3])));
			coursePrograms.add(courseProgram);

		}

		return coursePrograms;
	}

	public List<ScheduleModel> csvToSchedule(InputStream inputStream) {
		List<ScheduleModel> schedules = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Code", "TimeOfDay", "Range" };

		for (CSVRecord csvRecord : csvRecords) {

			ScheduleModel schedule = new ScheduleModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), 
					csvRecord.get(headers[0]), 
					csvRecord.get(headers[1]),
					csvRecord.get(headers[2]));
			schedules.add(schedule);

		}

		return schedules;
	}

	public List<ClassScheduleModel> csvToClassSchedule(InputStream inputStream) {
		List<ClassScheduleModel> classSchedules = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Class", "Day", "Schedule" };

		for (CSVRecord csvRecord : csvRecords) {

			ClassScheduleModel classSchedule = new ClassScheduleModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(), 
					classRepository.findByClassCode(csvRecord.get(headers[0])),
					dayRepository.findByDayNumber(Short.valueOf(csvRecord.get(headers[1]))),
					scheduleRepository.findByScheduleCode(csvRecord.get(headers[2])));
			classSchedules.add(classSchedule);

		}

		return classSchedules;
	}

	public List<CourseTypeModel> csvToCourseType(InputStream inputStream) {
		List<CourseTypeModel> courseTypes = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Name", "Value" };

		for (CSVRecord csvRecord : csvRecords) {

			CourseTypeModel courseType = new CourseTypeModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					csvRecord.get(headers[0]), 
					Short.valueOf(csvRecord.get(headers[1])));
			courseTypes.add(courseType);

		}

		return courseTypes;
	}

	public List<ClassModel> csvToClass(InputStream inputStream) {
		List<ClassModel> classes = new ArrayList<>();
		Iterable<CSVRecord> csvRecords = readFile(inputStream);
		String[] headers = { "Course", "Code" };

		for (CSVRecord csvRecord : csvRecords) {

			ClassModel classModel = new ClassModel(null, Instant.now(), Instant.now(), null,
					UUID.randomUUID().toString(),
					courseRepository.findByCourseCode(csvRecord.get(headers[0])),
					csvRecord.get(headers[1]));
			classes.add(classModel);

		}

		return classes;
	}

	private static Iterable<CSVRecord> readFile(InputStream inputStream) {

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			CSVFormat csvFormat = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true)
					.setIgnoreHeaderCase(true).setTrim(true).build();

			Iterable<CSVRecord> csvRecord = csvFormat.parse(fileReader);
			return csvRecord;
		} catch (IOException ex) {
			throw new RuntimeException("fail to parse CSV file: " + ex.getMessage());
		}

	}

}
