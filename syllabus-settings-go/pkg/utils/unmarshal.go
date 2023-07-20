package utils

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalCourses(value string) *[]models.Course {

	var courses []models.Course

	err := json.Unmarshal([]byte(value), &courses)
	if err != nil {
		panic(err)
	}

	return &courses
}

func UnmarshalCourse(value string) *models.Course {

	var course models.Course

	err := json.Unmarshal([]byte(value), &course)
	if err != nil {
		panic(err)
	}

	return &course
}

func UnmarshalClassSchedules(value string) *[]models.ClassSchedule {

	var classSchedules []models.ClassSchedule

	err := json.Unmarshal([]byte(value), &classSchedules)
	if err != nil {
		panic(err)
	}

	return &classSchedules
}

func UnmarshalClassSchedule(value string) *models.ClassSchedule {

	var classSchedule models.ClassSchedule

	err := json.Unmarshal([]byte(value), &classSchedule)
	if err != nil {
		panic(err)
	}

	return &classSchedule
}

func UnmarshalClasses(value string) *[]models.Class {

	var classes []models.Class

	err := json.Unmarshal([]byte(value), &classes)
	if err != nil {
		panic(err)
	}

	return &classes
}

func UnmarshalClass(value string) *models.Class {
	var class models.Class

	err := json.Unmarshal([]byte(value), &class)
	if err != nil {
		panic(err)
	}

	return &class
}

func UnmarshalPrograms(value string) *[]models.Program {
	var programs []models.Program

	err := json.Unmarshal([]byte(value), &programs)
	if err != nil {
		panic(err)
	}

	return &programs
}

func UnmarshalProgram(value string) *models.Program {
	var program models.Program

	err := json.Unmarshal([]byte(value), &program)
	if err != nil {
		panic(err)
	}

	return &program
}

func UnmarshalUniversities(value string) *[]models.University {
	var universities []models.University

	err := json.Unmarshal([]byte(value), &universities)
	if err != nil {
		panic(err)
	}

	return &universities
}

func UnmarshalUniversity(value string) *models.University {
	var university models.University

	err := json.Unmarshal([]byte(value), &university)
	if err != nil {
		panic(err)
	}

	return &university
}

func UnmarshalCoursePrograms(value string) *[]models.CourseProgram {
	var coursePrograms []models.CourseProgram

	err := json.Unmarshal([]byte(value), &coursePrograms)
	if err != nil {
		panic(err)
	}

	return &coursePrograms
}

func UnmarshalCourseProgram(value string) *models.CourseProgram {
	var courseProgram models.CourseProgram

	err := json.Unmarshal([]byte(value), &courseProgram)
	if err != nil {
		panic(err)
	}

	return &courseProgram
}

func UnmarshalCourseTypes(value string) *[]models.CourseType {
	var courseTypes []models.CourseType

	err := json.Unmarshal([]byte(value), &courseTypes)
	if err != nil {
		panic(err)
	}

	return &courseTypes
}

func UnmarshalCourseType(value string) *models.CourseType {
	var courseType models.CourseType

	err := json.Unmarshal([]byte(value), &courseType)
	if err != nil {
		panic(err)
	}

	return &courseType
}

func UnmarshalDays(value string) *[]models.Day {
	var days []models.Day

	err := json.Unmarshal([]byte(value), &days)
	if err != nil {
		panic(err)
	}

	return &days
}

func UnmarshalDay(value string) *models.Day {
	var day models.Day

	err := json.Unmarshal([]byte(value), &day)
	if err != nil {
		panic(err)
	}

	return &day
}

func UnmarshalSchedules(value string) *[]models.Schedule {
	var schedules []models.Schedule

	err := json.Unmarshal([]byte(value), &schedules)
	if err != nil {
		panic(err)
	}

	return &schedules
}

func UnmarshalSchedule(value string) *models.Schedule {
	var schedule models.Schedule

	err := json.Unmarshal([]byte(value), &schedule)
	if err != nil {
		panic(err)
	}

	return &schedule
}

func UnmarshalPreRequisiteCount(value string) *models.PreRequisiteCount {
	var preRequisiteCount models.PreRequisiteCount

	err := json.Unmarshal([]byte(value), &preRequisiteCount)
	if err != nil {
		panic(err)
	}

	return &preRequisiteCount
}
