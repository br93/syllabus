package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToDay(req *models.DayRequestModel) *models.Day {
	new := models.Day{DayId: uuid.NewString(), DayName: req.DayName, DayNumber: req.DayNumber}
	return &new
}

func ToDayArray(req *[]models.DayRequestModel) *[]models.Day {
	size := len((*req))
	var days = make([]models.Day, size)
	request := *req

	for i := 0; i < size; i++ {
		days[i] = *ToDay(&request[i])
	}

	return &days
}

func ToDayResponse(day *models.Day) *models.DayResponseModel {
	newResponse := models.DayResponseModel{DayId: day.DayId, DayName: day.DayName, DayNumber: day.DayNumber}
	return &newResponse
}

func ToDayResponseArray(req *[]models.Day) *[]models.DayResponseModel {
	size := len((*req))
	var days = make([]models.DayResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		days[i] = *ToDayResponse(&request[i])
	}

	return &days
}
