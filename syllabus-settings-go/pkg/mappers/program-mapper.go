package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToProgram(req *models.ProgramRequestModel) *models.Program {

	new := models.Program{ProgramId: uuid.NewString(), ProgramName: req.ProgramName, ProgramCode: req.ProgramCode, Terms: req.Terms}
	return &new
}

func ToProgramArray(req *[]models.ProgramRequestModel) *[]models.Program {
	size := len((*req))
	var programs = make([]models.Program, size)
	request := *req

	for i := 0; i < size; i++ {
		programs[i] = *ToProgram(&request[i])
	}

	return &programs
}

func ToProgramResponse(program *models.Program) *models.ProgramResponseModel {

	newResponse := models.ProgramResponseModel{ProgramId: program.ProgramId, ProgramCode: program.ProgramCode, ProgramName: program.ProgramName, Terms: program.Terms}
	return &newResponse
}

func ToProgramResponseArray(req *[]models.Program) *[]models.ProgramResponseModel {
	size := len((*req))
	var programs = make([]models.ProgramResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		programs[i] = *ToProgramResponse(&request[i])
	}

	return &programs
}

func ToProgramCourses(program *models.Program) *models.CoursesResponseModel {
	var disciplinas = program.Courses

	var response = ToCourseProgramResponseArray(&disciplinas)

	newResponse := models.CoursesResponseModel{ProgramId: program.ProgramId, ProgramCode: program.ProgramCode, ProgramName: program.ProgramName, Courses: *response}
	return &newResponse
}
