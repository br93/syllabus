package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalDays(value string) *[]models.DayResponseModel {
	var days []models.DayResponseModel

	err := json.Unmarshal([]byte(value), &days)
	if err != nil {
		panic(err)
	}

	return &days
}

func UnmarshalDay(value string) *models.DayResponseModel {
	var day models.DayResponseModel

	err := json.Unmarshal([]byte(value), &day)
	if err != nil {
		panic(err)
	}

	return &day
}
