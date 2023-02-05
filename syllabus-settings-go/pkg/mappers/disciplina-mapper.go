package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func FromDisciplinaCodigo(req *[]models.DisciplinaCodigoRequestModel) *[]models.DisciplinaEntity {
	size := len((*req))
	var disciplinas = make([]models.DisciplinaEntity, size)
	request := *req

	for i := 0; i < size; i++ {
		aux, _ := services.GetDisciplinaByCodigo(request[i].Codigo)
		disciplinas[i] = *aux
	}

	return &disciplinas
}

func ToDisciplinaEntity(req *models.DisciplinaRequestModel) *models.DisciplinaEntity {

	newEntity := models.DisciplinaEntity{DisciplinaId: uuid.NewString(), Nome: req.Nome, Codigo: req.Codigo, CargaHoraria: req.CargaHoraria}
	return &newEntity
}

func ToDisciplinaEntityArray(req *[]models.DisciplinaRequestModel) *[]models.DisciplinaEntity {
	size := len((*req))
	var disciplinas = make([]models.DisciplinaEntity, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinas[i] = *ToDisciplinaEntity(&request[i])
	}

	return &disciplinas
}

func ToDisciplinaResponse(disciplina *models.DisciplinaEntity) *models.DisciplinaResponseModel {

	newResponse := models.DisciplinaResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, CargaHoraria: disciplina.CargaHoraria}
	return &newResponse
}

func ToDisciplinaResponseArray(req *[]models.DisciplinaEntity) *[]models.DisciplinaResponseModel {
	size := len((*req))
	var disciplinas = make([]models.DisciplinaResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinas[i] = *ToDisciplinaResponse(&request[i])
	}

	return &disciplinas
}

func ToDisciplinaPreRequisito(disciplina *models.DisciplinaEntity) *models.DisciplinaPreRequisitosResponseModel {
	var preRequisitos = disciplina.PreRequisitos
	var response = ToDisciplinaResponseArray(preRequisitos)

	newResponse := models.DisciplinaPreRequisitosResponseModel{DisciplinaId: disciplina.DisciplinaId, Codigo: disciplina.Codigo, Nome: disciplina.Nome, CargaHoraria: disciplina.CargaHoraria, PreRequisitos: *response}
	return &newResponse
}
