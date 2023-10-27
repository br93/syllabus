package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToSchedule(req *models.ScheduleRequestModel) *models.Schedule {

	new := models.Schedule{ScheduleId: uuid.NewString(), ScheduleCode: req.ScheduleCode, TimeOfDay: req.TimeOfDay, TimeRange: req.TimeRange}
	return &new
}

func ToScheduleArray(req *[]models.ScheduleRequestModel) *[]models.Schedule {
	size := len((*req))
	var schedules = make([]models.Schedule, size)
	request := *req

	for i := 0; i < size; i++ {
		schedules[i] = *ToSchedule(&request[i])
	}

	return &schedules
}

func ToScheduleResponse(schedule *models.Schedule) *models.ScheduleResponseModel {

	newResponse := models.ScheduleResponseModel{ScheduleId: schedule.ScheduleId, ScheduleCode: schedule.ScheduleCode, TimeOfDay: schedule.TimeOfDay, TimeRange: schedule.TimeRange}
	return &newResponse
}

func ToScheduleResponseArray(req *[]models.Schedule) *[]models.ScheduleResponseModel {
	size := len((*req))
	var schedules = make([]models.ScheduleResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		schedules[i] = *ToScheduleResponse(&request[i])
	}

	return &schedules
}
