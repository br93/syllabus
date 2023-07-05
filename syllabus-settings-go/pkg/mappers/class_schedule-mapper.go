package mappers

import (
	"strconv"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToClassSchedule(req *models.ClassScheduleRequestModel) *models.ClassSchedule {

	class, errClass := services.GetClassByCode(req.ClassCode)
	day, errDay := services.GetDayByNumber(req.DayNumber)
	schedule, errSchedule := services.GetScheduleByCode(req.ScheduleCode)

	if errClass != nil || errDay != nil || errSchedule != nil {
		return &models.ClassSchedule{}
	}

	new := models.ClassSchedule{ClassScheduleId: uuid.NewString(), Class: *class, Day: *day, Schedule: *schedule}
	return &new
}

func ToClassScheduleArray(req *[]models.ClassScheduleRequestModel) *[]models.ClassSchedule {
	size := len((*req))
	var classSchedulees = make([]models.ClassSchedule, size)
	request := *req

	for i := 0; i < size; i++ {
		classSchedulees[i] = *ToClassSchedule(&request[i])
	}

	return &classSchedulees
}

func ToClassScheduleResponse(classSchedule *models.ClassSchedule) *models.ClassScheduleResponseModel {
	var schedule = strconv.FormatInt(int64(classSchedule.Day.DayNumber), 10) + classSchedule.Schedule.ScheduleCode
	newResponse := models.ClassScheduleResponseModel{ClassScheduleId: classSchedule.ClassScheduleId, ClassCode: classSchedule.Class.ClassCode, Schedule: schedule}
	return &newResponse
}

func ToClassScheduleResponseArray(req *[]models.ClassSchedule) *[]models.ClassScheduleResponseModel {
	size := len((*req))
	var classSchedulees = make([]models.ClassScheduleResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		classSchedulees[i] = *ToClassScheduleResponse(&request[i])
	}

	return &classSchedulees
}
