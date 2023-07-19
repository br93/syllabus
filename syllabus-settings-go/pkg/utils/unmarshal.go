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
