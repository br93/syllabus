package cache

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

type CourseProgramCache interface {
	SetCourseProgram(key string, value *models.CourseProgram)
	GetCourseProgram(key string) *models.CourseProgram
}
