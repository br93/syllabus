package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/google/uuid"
)

func ToCurso(req *models.CursoRequestModel) *models.Curso {

	new := models.Curso{CursoId: uuid.NewString(), Nome: req.Nome, Codigo: req.Codigo, Periodos: req.Periodos}
	return &new
}

func ToCursoArray(req *[]models.CursoRequestModel) *[]models.Curso {
	size := len((*req))
	var cursos = make([]models.Curso, size)
	request := *req

	for i := 0; i < size; i++ {
		cursos[i] = *ToCurso(&request[i])
	}

	return &cursos
}

func ToCursoResponse(curso *models.Curso) *models.CursoResponseModel {

	newResponse := models.CursoResponseModel{CursoId: curso.CursoId, Codigo: curso.Codigo, Nome: curso.Nome, Periodos: curso.Periodos}
	return &newResponse
}

func ToCursoResponseArray(req *[]models.Curso) *[]models.CursoResponseModel {
	size := len((*req))
	var cursos = make([]models.CursoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		cursos[i] = *ToCursoResponse(&request[i])
	}

	return &cursos
}

func ToCursoDisciplinas(curso *models.Curso) *models.CursoDisciplinasResponseModel {
	var disciplinas = curso.Disciplinas

	var response = ToDisciplinaCursoResponseArray(&disciplinas)

	newResponse := models.CursoDisciplinasResponseModel{CursoId: curso.CursoId, Codigo: curso.Codigo, Nome: curso.Nome, Disciplinas: *response}
	return &newResponse
}
