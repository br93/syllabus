package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalCourses(value string) *[]models.CourseResponseModel {

	var courses []models.CourseResponseModel

	err := json.Unmarshal([]byte(value), &courses)
	if err != nil {
		panic(err)
	}

	return &courses
}

func UnmarshalCourse(value string) *models.CourseResponseModel {

	var course models.CourseResponseModel

	err := json.Unmarshal([]byte(value), &course)
	if err != nil {
		panic(err)
	}

	return &course
}

func UnmarshalCourseClasses(value string) *models.CourseClassesResponseModel {

	var courseClasses models.CourseClassesResponseModel

	err := json.Unmarshal([]byte(value), &courseClasses)
	if err != nil {
		panic(err)
	}

	return &courseClasses
}

func UnmarshalPreRequisiteCourses(value string) *models.PreRequisiteCoursesResponseModel {
	var preRequisiteCourses models.PreRequisiteCoursesResponseModel

	err := json.Unmarshal([]byte(value), &preRequisiteCourses)
	if err != nil {
		panic(err)
	}

	return &preRequisiteCourses
}
