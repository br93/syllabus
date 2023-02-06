package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToHorario(req *models.HorarioRequestModel) *models.Horario {

	new := models.Horario{HorarioId: uuid.NewString(), Sigla: req.Sigla, Faixa: req.Faixa}
	return &new
}

func ToHorarioArray(req *[]models.HorarioRequestModel) *[]models.Horario {
	size := len((*req))
	var horarios = make([]models.Horario, size)
	request := *req

	for i := 0; i < size; i++ {
		horarios[i] = *ToHorario(&request[i])
	}

	return &horarios
}

func ToHorarioResponse(horario *models.Horario) *models.HorarioResponseModel {

	newResponse := models.HorarioResponseModel{HorarioId: horario.HorarioId, Sigla: horario.Sigla, Faixa: horario.Faixa}
	return &newResponse
}

func ToHorarioResponseArray(req *[]models.Horario) *[]models.HorarioResponseModel {
	size := len((*req))
	var horarios = make([]models.HorarioResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		horarios[i] = *ToHorarioResponse(&request[i])
	}

	return &horarios
}
