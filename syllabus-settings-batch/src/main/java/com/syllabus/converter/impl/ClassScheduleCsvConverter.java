package com.syllabus.converter.impl;

import java.io.InputStream;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import com.syllabus.converter.CsvConverter;
import com.syllabus.helper.CsvHelper;
import com.syllabus.helper.StringManipulator;
import com.syllabus.helper.Validator;
import com.syllabus.model.ClassModel;
import com.syllabus.model.ClassScheduleModel;
import com.syllabus.model.DayModel;
import com.syllabus.model.ScheduleModel;
import com.syllabus.service.impl.ClassService;
import com.syllabus.service.impl.DayService;
import com.syllabus.service.impl.ScheduleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClassScheduleCsvConverter implements CsvConverter<ClassScheduleModel> {

	private final ClassService classService;
	private final DayService dayService;
	private final ScheduleService scheduleService;

	public List<ClassScheduleModel> csvToModel(InputStream inputStream) {
		Set<ClassScheduleModel> classSchedules = new HashSet<>();
		Iterable<CSVRecord> csvRecords = CsvHelper.readFile(inputStream);
		String[] headers = { "Course", "Class", "Qtd", "S1", "S2", "S3", "S4", "S5", "S6" };

		for (CSVRecord csvRecord : csvRecords)
			parseColumns(extractClassCode(csvRecord.get(headers[0]), csvRecord.get(headers[1])),
					List.of(csvRecord.get(headers[3]), csvRecord.get(headers[4]), csvRecord.get(headers[5]),
							csvRecord.get(headers[6]), csvRecord.get(headers[7]), csvRecord.get(headers[8])),
					classSchedules);

		return classSchedules.stream().collect(Collectors.toList());
	}

	private ClassScheduleModel createClassSchedule(String classCode, String schedule) {

		if (Validator.isEmpty(schedule))
			return new ClassScheduleModel();

		ClassModel classModel = classService.findByClassCode(classCode);
		DayModel day = dayService.findByDayNumber(Short.valueOf(StringManipulator.extractPrefix(schedule)));


		String scheduleString = StringManipulator.extractSufix(schedule);
		ScheduleModel newSchedule = scheduleService.findByScheduleCode(scheduleString);

		return new ClassScheduleModel(null, Instant.now(), Instant.now(), null, UUID.randomUUID().toString(),
				classModel, day, newSchedule, classCode, StringManipulator.extractPrefix(scheduleString));

	}

	private String extractClassCode(String course, String code) {
		return course.concat("-").concat(code);
	}

	
	private void parseColumns(String code, List<String> schedules,
			Set<ClassScheduleModel> set) {

		schedules.forEach(schedule -> set.add(this.createClassSchedule(code, schedule)));
		this.trimSet(set);
	}

	private void trimSet(Set<ClassScheduleModel> set) {
		var nullPreRequisite = new ClassScheduleModel();
		set.remove(nullPreRequisite);
	}

}
