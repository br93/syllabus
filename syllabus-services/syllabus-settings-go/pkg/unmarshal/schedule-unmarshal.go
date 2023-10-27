package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalSchedules(value string) *[]models.ScheduleResponseModel {
	var schedules []models.ScheduleResponseModel

	err := json.Unmarshal([]byte(value), &schedules)
	if err != nil {
		panic(err)
	}

	return &schedules
}

func UnmarshalSchedule(value string) *models.ScheduleResponseModel {
	var schedule models.ScheduleResponseModel

	err := json.Unmarshal([]byte(value), &schedule)
	if err != nil {
		panic(err)
	}

	return &schedule
}
