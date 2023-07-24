package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalUniversities(value string) *[]models.UniversityResponseModel {
	var universities []models.UniversityResponseModel

	err := json.Unmarshal([]byte(value), &universities)
	if err != nil {
		panic(err)
	}

	return &universities
}

func UnmarshalUniversity(value string) *models.UniversityResponseModel {
	var university models.UniversityResponseModel

	err := json.Unmarshal([]byte(value), &university)
	if err != nil {
		panic(err)
	}

	return &university
}

func UnmarshalUniversityCourses(value string) *models.UniversityCoursesResponseModel {
	var universityCourses models.UniversityCoursesResponseModel

	err := json.Unmarshal([]byte(value), &universityCourses)
	if err != nil {
		panic(err)
	}

	return &universityCourses
}

func UnmarshalUniversityPrograms(value string) *models.UniversityProgramsResponseModel {
	var universityPrograms models.UniversityProgramsResponseModel

	err := json.Unmarshal([]byte(value), &universityPrograms)
	if err != nil {
		panic(err)
	}

	return &universityPrograms
}
