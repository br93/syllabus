package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToCourseType(req *models.CourseTypeRequestModel) *models.CourseType {

	new := models.CourseType{TypeId: uuid.NewString(), TypeName: req.TypeName, TypeValue: req.TypeValue}
	return &new
}

func ToCourseTypeArray(req *[]models.CourseTypeRequestModel) *[]models.CourseType {
	size := len((*req))
	var courseTypes = make([]models.CourseType, size)
	request := *req

	for i := 0; i < size; i++ {
		courseTypes[i] = *ToCourseType(&request[i])
	}

	return &courseTypes
}

func ToCourseTypeResponse(courseType *models.CourseType) *models.CourseTypeResponseModel {

	newResponse := models.CourseTypeResponseModel{TypeId: courseType.TypeId, TypeName: courseType.TypeName}
	return &newResponse
}

func ToCourseTypeResponseArray(req *[]models.CourseType) *[]models.CourseTypeResponseModel {
	size := len((*req))
	var courseTypes = make([]models.CourseTypeResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		courseTypes[i] = *ToCourseTypeResponse(&request[i])
	}

	return &courseTypes
}
