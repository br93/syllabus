package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToDia(req *models.DiaRequestModel) *models.Dia {
	new := models.Dia{DiaId: uuid.NewString(), DiaNome: req.DiaNome, DiaNumero: req.DiaNumero}
	return &new
}

func ToDiaArray(req *[]models.DiaRequestModel) *[]models.Dia {
	size := len((*req))
	var dias = make([]models.Dia, size)
	request := *req

	for i := 0; i < size; i++ {
		dias[i] = *ToDia(&request[i])
	}

	return &dias
}

func ToDiaResponse(dia *models.Dia) *models.DiaResponseModel {
	newResponse := models.DiaResponseModel{DiaId: dia.DiaId, DiaNome: dia.DiaNome, DiaNumero: dia.DiaNumero}
	return &newResponse
}

func ToDiaResponseArray(req *[]models.Dia) *[]models.DiaResponseModel {
	size := len((*req))
	var dias = make([]models.DiaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		dias[i] = *ToDiaResponse(&request[i])
	}

	return &dias
}
