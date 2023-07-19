package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func FromCourseCode(req *[]models.CourseCodeRequestModel) *[]models.Course {
	size := len((*req))
	var courses = make([]models.Course, size)
	request := *req

	for i := 0; i < size; i++ {
		aux, _ := services.GetCourseByCode(request[i].CourseCode)
		courses[i] = *aux
	}

	return &courses
}

func ToCourse(req *models.CourseRequestModel) *models.Course {

	university, errUniversity := services.GetUniversityByCode(req.UniversityCode)

	if errUniversity != nil {
		return &models.Course{}
	}

	new := models.Course{CourseId: uuid.NewString(), CourseName: req.CourseName, CourseCode: req.CourseCode, Workload: req.Workload, University: *university}
	return &new
}

func ToCourseArray(req *[]models.CourseRequestModel) *[]models.Course {
	size := len((*req))
	var courses = make([]models.Course, size)
	request := *req

	for i := 0; i < size; i++ {
		courses[i] = *ToCourse(&request[i])
	}

	return &courses
}

func ToCourseResponse(course *models.Course) *models.CourseResponseModel {

	newResponse := models.CourseResponseModel{CourseId: course.CourseId, CourseCode: course.CourseCode, CourseName: course.CourseName, Workload: course.Workload, UniversityCode: course.University.UniversityCode}
	return &newResponse
}

func ToCourseResponseArray(req *[]models.Course) *[]models.CourseResponseModel {

	if req == nil {
		return nil
	}

	size := len((*req))
	var courses = make([]models.CourseResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		courses[i] = *ToCourseResponse(&request[i])
	}

	return &courses
}

func ToCoursePreRequisite(course *models.Course) *models.PreRequisiteCoursesResponseModel {
	var preRequisites = course.PreRequisiteCourses
	var response = ToCourseResponseArray(preRequisites)

	newResponse := models.PreRequisiteCoursesResponseModel{CourseId: course.CourseId, CourseCode: course.CourseCode, CourseName: course.CourseName, Workload: course.Workload, PreRequisiteCourses: *response}
	return &newResponse
}

func ToCourseEquivalent(course *models.Course) *models.EquivalentCoursesResponseModel {
	var equivalents = course.EquivalentCourses
	var response = ToCourseResponseArray(equivalents)

	newResponse := models.EquivalentCoursesResponseModel{CourseId: course.CourseId, CourseCode: course.CourseCode, CourseName: course.CourseName, Workload: course.Workload, EquivalentCourses: *response}
	return &newResponse
}

func ToCourseClasses(course *models.Course) *models.CourseClassesResponseModel {
	var turmas = course.Classes

	var response = ToClassResponseArray(&turmas)

	newResponse := models.CourseClassesResponseModel{CourseId: course.CourseId, CourseCode: course.CourseCode, CourseName: course.CourseName, Classes: *response}
	return &newResponse
}
