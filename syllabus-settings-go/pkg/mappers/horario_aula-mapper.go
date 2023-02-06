package mappers

import (
	"strconv"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToHorarioAula(req *models.HorarioAulaRequestModel) *models.HorarioAula {

	turma, errTurma := services.GetTurmaByCodigo(req.Turma)
	dia, errDia := services.GetDiaByNumero(req.Dia)
	horario, errHorario := services.GetHorarioBySigla(req.Horario)

	if errTurma != nil || errDia != nil || errHorario != nil {
		return &models.HorarioAula{}
	}

	new := models.HorarioAula{HorarioAulaId: uuid.NewString(), Turma: *turma, Dia: *dia, Horario: *horario}
	return &new
}

func ToHorarioAulaArray(req *[]models.HorarioAulaRequestModel) *[]models.HorarioAula {
	size := len((*req))
	var horarioaulas = make([]models.HorarioAula, size)
	request := *req

	for i := 0; i < size; i++ {
		horarioaulas[i] = *ToHorarioAula(&request[i])
	}

	return &horarioaulas
}

func ToHorarioAulaResponse(horarioaula *models.HorarioAula) *models.HorarioAulaResponseModel {
	var horario = strconv.FormatInt(int64(horarioaula.Dia.DiaNumero), 10) + horarioaula.Horario.Sigla
	newResponse := models.HorarioAulaResponseModel{HorarioAulaId: horarioaula.HorarioAulaId, Turma: horarioaula.Turma.Codigo, Horario: horario}
	return &newResponse
}

func ToHorarioAulaResponseArray(req *[]models.HorarioAula) *[]models.HorarioAulaResponseModel {
	size := len((*req))
	var horarioaulas = make([]models.HorarioAulaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		horarioaulas[i] = *ToHorarioAulaResponse(&request[i])
	}

	return &horarioaulas
}
