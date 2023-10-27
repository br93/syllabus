package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalClassSchedules(value string) *[]models.ClassScheduleResponseModel {

	var classSchedules []models.ClassScheduleResponseModel

	err := json.Unmarshal([]byte(value), &classSchedules)
	if err != nil {
		panic(err)
	}

	return &classSchedules
}

func UnmarshalClassSchedule(value string) *models.ClassScheduleResponseModel {

	var classSchedule models.ClassScheduleResponseModel

	err := json.Unmarshal([]byte(value), &classSchedule)
	if err != nil {
		panic(err)
	}

	return &classSchedule
}
