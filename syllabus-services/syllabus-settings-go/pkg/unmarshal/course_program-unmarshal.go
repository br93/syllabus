package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalCourseProgramResponseArray(value string) *[]models.CourseProgramResponseModel {
	var coursePrograms []models.CourseProgramResponseModel

	err := json.Unmarshal([]byte(value), &coursePrograms)
	if err != nil {
		panic(err)
	}

	return &coursePrograms
}

func UnmarshalCourseProgramResponse(value string) *models.CourseProgramResponseModel {
	var courseProgram models.CourseProgramResponseModel

	err := json.Unmarshal([]byte(value), &courseProgram)
	if err != nil {
		panic(err)
	}

	return &courseProgram
}
