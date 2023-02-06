package mappers

import (
	"fmt"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToTurma(req *models.TurmaRequestModel) *models.Turma {

	disciplina, errDisciplina := services.GetDisciplinaByCodigo(req.Disciplina)
	turno, errTurno := services.GetTurnoByNome(req.Turno)

	if errDisciplina != nil || errTurno != nil {
		return &models.Turma{}
	}

	new := models.Turma{TurmaId: uuid.NewString(), Codigo: req.Codigo, Turno: *turno, Disciplina: *disciplina}
	return &new
}

func ToTurmaArray(req *[]models.TurmaRequestModel) *[]models.Turma {
	size := len((*req))
	var turmas = make([]models.Turma, size)
	request := *req

	for i := 0; i < size; i++ {
		turmas[i] = *ToTurma(&request[i])
	}

	return &turmas
}

func ToTurmaResponse(turma *models.Turma) *models.TurmaResponseModel {
	turnoNome := turma.Turno.TurnoId
	fmt.Println("turno " + turnoNome)

	newResponse := models.TurmaResponseModel{TurmaId: turma.TurmaId, Codigo: turma.Codigo, Turno: turnoNome}
	return &newResponse
}

func ToTurmaResponseArray(req *[]models.Turma) *[]models.TurmaResponseModel {
	size := len((*req))
	var turmas = make([]models.TurmaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		turmas[i] = *ToTurmaResponse(&request[i])
	}

	return &turmas
}
