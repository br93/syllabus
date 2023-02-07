package mappers

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/google/uuid"
)

func ToDisciplinaCurso(req *models.DisciplinaCursoRequestModel) *models.DisciplinaCurso {
	curso, errCurso := services.GetCursoByCodigo(req.Curso)
	disciplina, errDisciplina := services.GetDisciplinaByCodigo(req.Disciplina)
	tipo, errTipo := services.GetTipoByNome(req.Tipo)

	if errCurso != nil || errDisciplina != nil || errTipo != nil {
		return &models.DisciplinaCurso{}
	}
	new := models.DisciplinaCurso{DisciplinaCursoId: uuid.NewString(), Curso: *curso, Disciplina: *disciplina, Tipo: *tipo, Periodo: req.Periodo}
	return &new
}

func ToDisciplinaCursoArray(req *[]models.DisciplinaCursoRequestModel) *[]models.DisciplinaCurso {
	size := len((*req))
	var disciplinacursos = make([]models.DisciplinaCurso, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinacursos[i] = *ToDisciplinaCurso(&request[i])
	}

	return &disciplinacursos
}

func ToDisciplinaCursoResponse(disciplinacurso *models.DisciplinaCurso) *models.DisciplinaCursoResponseModel {

	newResponse := models.DisciplinaCursoResponseModel{DisciplinaCursoId: disciplinacurso.DisciplinaCursoId, Disciplina: disciplinacurso.Disciplina.Codigo, Tipo: disciplinacurso.Tipo.TipoNome, Periodo: disciplinacurso.Periodo}
	return &newResponse
}

func ToDisciplinaCursoResponseArray(req *[]models.DisciplinaCurso) *[]models.DisciplinaCursoResponseModel {
	size := len((*req))
	var disciplinacursos = make([]models.DisciplinaCursoResponseModel, size)
	request := *req

	for i := 0; i < size; i++ {
		disciplinacursos[i] = *ToDisciplinaCursoResponse(&request[i])
	}

	return &disciplinacursos
}
