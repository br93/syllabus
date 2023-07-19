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
