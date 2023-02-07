package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToTipo(req *models.TipoRequestModel) *models.Tipo {

	new := models.Tipo{TipoId: uuid.NewString(), TipoNome: req.TipoNome, TipoValor: req.TipoValor}
	return &new
}

func ToTipoArray(req *[]models.TipoRequestModel) *[]models.Tipo {
	size := len((*req))
	var tipos = make([]models.Tipo, size)
	request := *req

	for i := 0; i < size; i++ {
		tipos[i] = *ToTipo(&request[i])
	}

	return &tipos
}

func ToTipoResponse(tipo *models.Tipo) *models.TipoResponseModel {

	newResponse := models.TipoResponseModel{TipoId: tipo.TipoId, TipoNome: tipo.TipoNome}
	return &newResponse
}

func ToTipoResponseArray(req *[]models.Tipo) *[]models.TipoResponseModel {
	size := len((*req))
	var tipos = make([]models.TipoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		tipos[i] = *ToTipoResponse(&request[i])
	}

	return &tipos
}
