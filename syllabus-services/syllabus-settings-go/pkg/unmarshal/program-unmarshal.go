package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalPrograms(value string) *[]models.ProgramResponseModel {
	var programs []models.ProgramResponseModel

	err := json.Unmarshal([]byte(value), &programs)
	if err != nil {
		panic(err)
	}

	return &programs
}

func UnmarshalProgram(value string) *models.ProgramResponseModel {
	var program models.ProgramResponseModel

	err := json.Unmarshal([]byte(value), &program)
	if err != nil {
		panic(err)
	}

	return &program
}

func UnmarshalProgramCourses(value string) *models.ProgramCoursesResponseModel {
	var programCourses models.ProgramCoursesResponseModel

	err := json.Unmarshal([]byte(value), &programCourses)
	if err != nil {
		panic(err)
	}

	return &programCourses
}
