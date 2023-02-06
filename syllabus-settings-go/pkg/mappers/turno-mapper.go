package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToTurno(req *models.TurnoRequestModel) *models.Turno {

	new := models.Turno{TurnoId: uuid.NewString(), TurnoNome: req.TurnoNome, TurnoValor: req.TurnoValor}
	return &new
}

func ToTurnoArray(req *[]models.TurnoRequestModel) *[]models.Turno {
	size := len((*req))
	var turnos = make([]models.Turno, size)
	request := *req

	for i := 0; i < size; i++ {
		turnos[i] = *ToTurno(&request[i])
	}

	return &turnos
}

func ToTurnoResponse(turno *models.Turno) *models.TurnoResponseModel {

	newResponse := models.TurnoResponseModel{TurnoId: turno.TurnoId, TurnoNome: turno.TurnoNome}
	return &newResponse
}

func ToTurnoResponseArray(req *[]models.Turno) *[]models.TurnoResponseModel {
	size := len((*req))
	var turnos = make([]models.TurnoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		turnos[i] = *ToTurnoResponse(&request[i])
	}

	return &turnos
}
