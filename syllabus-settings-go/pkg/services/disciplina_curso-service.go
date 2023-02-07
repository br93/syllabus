package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"gorm.io/gorm/clause"
)

func CreateDisciplinaCurso(req *models.DisciplinaCurso) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDisciplinaCursoById(disciplinacursoId string) (*models.DisciplinaCurso, error) {
	var disciplinacurso models.DisciplinaCurso

	models.DB.Preload(clause.Associations).First(&disciplinacurso, "disciplinacurso_id", disciplinacursoId)

	if disciplinacurso.ID == 0 {
		return &disciplinacurso, errors.New("disciplinacurso not found")
	}

	return &disciplinacurso, nil
}

func GetDisciplinaCursos() (*[]models.DisciplinaCurso, error) {

	var disciplinacursos []models.DisciplinaCurso

	result := models.DB.Find(&disciplinacursos)

	if result.Error != nil {
		return &disciplinacursos, result.Error
	}

	return &disciplinacursos, nil
}

func UpdateDisciplinaCurso(disciplinacursoId string, req *models.DisciplinaCurso) (*models.DisciplinaCurso, error) {
	response, err := GetDisciplinaCursoById(disciplinacursoId)

	if err != nil {
		return req, err
	}

	response.Disciplina = req.Disciplina
	response.Curso = req.Curso
	response.Tipo = req.Tipo
	response.Periodo = req.Periodo

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteDisciplinaCurso(disciplinacursoId string) error {
	get, err := GetDisciplinaCursoById(disciplinacursoId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
