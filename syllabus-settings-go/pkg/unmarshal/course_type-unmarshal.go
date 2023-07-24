package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalCourseTypes(value string) *[]models.CourseTypeResponseModel {
	var courseTypes []models.CourseTypeResponseModel

	err := json.Unmarshal([]byte(value), &courseTypes)
	if err != nil {
		panic(err)
	}

	return &courseTypes
}

func UnmarshalCourseType(value string) *models.CourseTypeResponseModel {
	var courseType models.CourseTypeResponseModel

	err := json.Unmarshal([]byte(value), &courseType)
	if err != nil {
		panic(err)
	}

	return &courseType
}
