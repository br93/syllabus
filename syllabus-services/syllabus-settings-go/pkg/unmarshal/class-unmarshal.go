package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalClasses(value string) *[]models.ClassResponseModel {

	var classes []models.ClassResponseModel

	err := json.Unmarshal([]byte(value), &classes)
	if err != nil {
		panic(err)
	}

	return &classes
}

func UnmarshalClass(value string) *models.ClassResponseModel {
	var class models.ClassResponseModel

	err := json.Unmarshal([]byte(value), &class)
	if err != nil {
		panic(err)
	}

	return &class
}
