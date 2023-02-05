package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToTurnoEntity(req *models.TurnoRequestModel) *models.TurnoEntity {

	newEntity := models.TurnoEntity{TurnoId: uuid.NewString(), TurnoNome: req.TurnoNome, TurnoValor: req.TurnoValor}
	return &newEntity
}

func ToTurnoEntityArray(req *[]models.TurnoRequestModel) *[]models.TurnoEntity {
	size := len((*req))
	var turnos = make([]models.TurnoEntity, size)
	request := *req

	for i := 0; i < size; i++ {
		turnos[i] = *ToTurnoEntity(&request[i])
	}

	return &turnos
}

func ToTurnoResponse(turno *models.TurnoEntity) *models.TurnoResponseModel {

	newResponse := models.TurnoResponseModel{TurnoId: turno.TurnoId, TurnoNome: turno.TurnoNome}
	return &newResponse
}

func ToTurnoResponseArray(req *[]models.TurnoEntity) *[]models.TurnoResponseModel {
	size := len((*req))
	var turnos = make([]models.TurnoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		turnos[i] = *ToTurnoResponse(&request[i])
	}

	return &turnos
}
