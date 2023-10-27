package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToUniversity(req *models.UniversityRequestModel) *models.University {

	new := models.University{UniversityId: uuid.NewString(), UniversityName: req.UniversityName, UniversityCode: req.UniversityCode}
	return &new
}

func ToUniversityArray(req *[]models.UniversityRequestModel) *[]models.University {
	size := len((*req))
	var universitys = make([]models.University, size)
	request := *req

	for i := 0; i < size; i++ {
		universitys[i] = *ToUniversity(&request[i])
	}

	return &universitys
}

func ToUniversityResponse(university *models.University) *models.UniversityResponseModel {

	newResponse := models.UniversityResponseModel{UniversityId: university.UniversityId, UniversityCode: university.UniversityCode, UniversityName: university.UniversityName}
	return &newResponse
}

func ToUniversityResponseArray(req *[]models.University) *[]models.UniversityResponseModel {
	size := len((*req))
	var universitys = make([]models.UniversityResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		universitys[i] = *ToUniversityResponse(&request[i])
	}

	return &universitys
}

func ToUniversityPrograms(university *models.University) *models.UniversityProgramsResponseModel {
	var programs = university.Programs

	var response = ToProgramResponseArray(&programs)

	newResponse := models.UniversityProgramsResponseModel{UniversityId: university.UniversityId, UniversityCode: university.UniversityCode, UniversityName: university.UniversityName, Programs: *response}
	return &newResponse
}

func ToUniversityCourses(university *models.University) *models.UniversityCoursesResponseModel {
	var courses = university.Courses

	var response = ToCourseResponseArray(&courses)

	newResponse := models.UniversityCoursesResponseModel{UniversityId: university.UniversityId, UniversityCode: university.UniversityCode, UniversityName: university.UniversityName, Courses: *response}
	return &newResponse
}
