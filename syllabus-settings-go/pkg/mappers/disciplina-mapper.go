package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func FromDisciplinaCodigo(req *[]models.DisciplinaCodigoRequestModel) *[]models.Disciplina {
	size := len((*req))
	var disciplinas = make([]models.Disciplina, size)
	request := *req

	for i := 0; i < size; i++ {
		aux, _ := services.GetDisciplinaByCodigo(request[i].Codigo)
		disciplinas[i] = *aux
	}

	return &disciplinas
}

func ToDisciplina(req *models.DisciplinaRequestModel) *models.Disciplina {

	new := models.Disciplina{DisciplinaId: uuid.NewString(), Nome: req.Nome, Codigo: req.Codigo, CargaHoraria: req.CargaHoraria}
	return &new
}

func ToDisciplinaArray(req *[]models.DisciplinaRequestModel) *[]models.Disciplina {
	size := len((*req))
	var disciplinas = make([]models.Disciplina, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinas[i] = *ToDisciplina(&request[i])
	}

	return &disciplinas
}

func ToDisciplinaResponse(disciplina *models.Disciplina) *models.DisciplinaResponseModel {

	newResponse := models.DisciplinaResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, CargaHoraria: disciplina.CargaHoraria}
	return &newResponse
}

func ToDisciplinaResponseArray(req *[]models.Disciplina) *[]models.DisciplinaResponseModel {
	size := len((*req))
	var disciplinas = make([]models.DisciplinaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinas[i] = *ToDisciplinaResponse(&request[i])
	}

	return &disciplinas
}

func ToDisciplinaPreRequisito(disciplina *models.Disciplina) *models.DisciplinaPreRequisitosResponseModel {
	var preRequisitos = disciplina.PreRequisitos
	var response = ToDisciplinaResponseArray(preRequisitos)

	newResponse := models.DisciplinaPreRequisitosResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, CargaHoraria: disciplina.CargaHoraria, PreRequisitos: *response}
	return &newResponse
}

func ToDisciplinaEquivalente(disciplina *models.Disciplina) *models.DisciplinaEquivalentesResponseModel {
	var equivalentes = disciplina.Equivalentes
	var response = ToDisciplinaResponseArray(equivalentes)

	newResponse := models.DisciplinaEquivalentesResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, CargaHoraria: disciplina.CargaHoraria, Equivalentes: *response}
	return &newResponse
}

func ToDisciplinaTurmas(disciplina *models.Disciplina) *models.DisciplinaTurmasResponseModel {
	var turmas = disciplina.Turmas

	var response = ToTurmaResponseArray(&turmas)

	newResponse := models.DisciplinaTurmasResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, Turmas: *response}
	return &newResponse
}
