package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToCourseProgram(req *models.CourseProgramRequestModel) *models.CourseProgram {
	program, errProgram := services.GetProgramByCode(req.Program)
	course, errCourse := services.GetCourseByCode(req.Course)
	courseType, errCourseType := services.GetCourseTypeByName(req.CourseType)

	if errProgram != nil || errCourse != nil || errCourseType != nil {
		return &models.CourseProgram{}
	}
	new := models.CourseProgram{CourseProgramId: uuid.NewString(), Program: *program, Course: *course, CourseType: *courseType, Term: req.Term}
	return &new
}

func ToCourseProgramArray(req *[]models.CourseProgramRequestModel) *[]models.CourseProgram {
	size := len((*req))
	var courseprograms = make([]models.CourseProgram, size)
	request := *req

	for i := 0; i < size; i++ {
		courseprograms[i] = *ToCourseProgram(&request[i])
	}

	return &courseprograms
}

func ToCourseProgramResponse(courseprogram *models.CourseProgram) *models.CourseProgramResponseModel {

	newResponse := models.CourseProgramResponseModel{CourseProgramId: courseprogram.CourseProgramId, Course: courseprogram.Course.Code, CourseType: courseprogram.CourseType.CourseTypeName, Term: courseprogram.Term}
	return &newResponse
}

func ToCourseProgramResponseArray(req *[]models.CourseProgram) *[]models.CourseProgramResponseModel {
	size := len((*req))
	var courseprograms = make([]models.CourseProgramResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		courseprograms[i] = *ToCourseProgramResponse(&request[i])
	}

	return &courseprograms
}
