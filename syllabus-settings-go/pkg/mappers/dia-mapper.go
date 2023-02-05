package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToDiaEntity(req *models.DiaRequestModel) *models.DiaEntity {
	newEntity := models.DiaEntity{DiaId: uuid.NewString(), DiaNome: req.DiaNome, DiaNumero: req.DiaNumero}
	return &newEntity
}

func ToDiaEntityArray(req *[]models.DiaRequestModel) *[]models.DiaEntity {
	size := len((*req))
	var dias = make([]models.DiaEntity, size)
	request := *req

	for i := 0; i < size; i++ {
		dias[i] = *ToDiaEntity(&request[i])
	}

	return &dias
}

func ToDiaResponse(dia *models.DiaEntity) *models.DiaResponseModel {
	newResponse := models.DiaResponseModel{DiaId: dia.DiaId, DiaNome: dia.DiaNome, DiaNumero: dia.DiaNumero}
	return &newResponse
}

func ToDiaResponseArray(req *[]models.DiaEntity) *[]models.DiaResponseModel {
	size := len((*req))
	var dias = make([]models.DiaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		dias[i] = *ToDiaResponse(&request[i])
	}

	return &dias
}
