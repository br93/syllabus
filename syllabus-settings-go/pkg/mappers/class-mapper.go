package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToClass(req *models.ClassRequestModel) *models.Class {

	course, errCourse := services.GetCourseByCode(req.Course)

	if errCourse != nil {
		return &models.Class{}
	}

	new := models.Class{ClassId: uuid.NewString(), ClassCode: req.ClassCode, Course: *course}
	return &new
}

func ToClassArray(req *[]models.ClassRequestModel) *[]models.Class {
	size := len((*req))
	var classes = make([]models.Class, size)
	request := *req

	for i := 0; i < size; i++ {
		classes[i] = *ToClass(&request[i])
	}

	return &classes
}

func ToClassResponse(class *models.Class) *models.ClassResponseModel {

	newResponse := models.ClassResponseModel{ClassId: class.ClassId, ClassCode: class.ClassCode}
	return &newResponse
}

func ToClassResponseArray(req *[]models.Class) *[]models.ClassResponseModel {
	size := len((*req))
	var classes = make([]models.ClassResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		classes[i] = *ToClassResponse(&request[i])
	}

	return &classes
}

func ToClassSchedules(class *models.Class) *models.ClassSchedulesResponseModel {
	var classSchedules = class.ClassSchedules

	var response = ToClassScheduleResponseArray(&classSchedules)

	newResponse := models.ClassSchedulesResponseModel{ClassId: class.ClassId, Course: class.Course.CourseCode, ClassSchedules: *response}
	return &newResponse
}
