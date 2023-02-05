package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToCursoEntity(req *models.CursoRequestModel) *models.CursoEntity {

	newEntity := models.CursoEntity{CursoId: uuid.NewString(), Nome: req.Nome, Codigo: req.Codigo}
	return &newEntity
}

func ToCursoEntityArray(req *[]models.CursoRequestModel) *[]models.CursoEntity {
	size := len((*req))
	var cursos = make([]models.CursoEntity, size)
	request := *req

	for i := 0; i < size; i++ {
		cursos[i] = *ToCursoEntity(&request[i])
	}

	return &cursos
}

func ToCursoResponse(curso *models.CursoEntity) *models.CursoResponseModel {

	newResponse := models.CursoResponseModel{CursoId: curso.CursoId, Codigo: curso.Codigo, Nome: curso.Nome}
	return &newResponse
}

func ToCursoResponseArray(req *[]models.CursoEntity) *[]models.CursoResponseModel {
	size := len((*req))
	var cursos = make([]models.CursoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		cursos[i] = *ToCursoResponse(&request[i])
	}

	return &cursos
}
