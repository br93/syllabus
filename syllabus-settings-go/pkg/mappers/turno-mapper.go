package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToTurnoEntity(req *models.TurnoRequestModel) *models.TurnoEntity {

	newEntity := models.TurnoEntity{TurnoId: uuid.NewString(), TurnoNome: req.TurnoNome, TurnoValor: req.TurnoValor}
	return &newEntity
}

func ToTurnoResponse(turno *models.TurnoEntity) *models.TurnoResponseModel {

	newResponse := models.TurnoResponseModel{TurnoId: turno.TurnoId, TurnoNome: turno.TurnoNome}
	return &newResponse
}
