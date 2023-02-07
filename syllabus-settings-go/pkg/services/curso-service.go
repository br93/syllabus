package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"gorm.io/gorm/clause"
)

func CreateCurso(req *models.Curso) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetCursoById(cursoId string, preload ...string) (*models.Curso, error) {
	var curso models.Curso

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&curso, "curso_id", cursoId)

	if curso.ID == 0 {
		return &curso, errors.New("curso not found")
	}

	return &curso, nil
}

func GetCursoByCodigo(codigo string) (*models.Curso, error) {
	var curso models.Curso

	models.DB.First(&curso, "codigo", codigo)

	if curso.ID == 0 {
		return &curso, errors.New("curso not found")
	}

	return &curso, nil
}

func GetCursos() (*[]models.Curso, error) {
	var cursos []models.Curso

	result := models.DB.Find(&cursos)

	if result.Error != nil {
		return &cursos, result.Error
	}

	return &cursos, nil
}

func UpdateCurso(cursoId string, req *models.Curso) (*models.Curso, error) {
	response, err := GetCursoById(cursoId)

	if err != nil {
		return req, err
	}

	response.Nome = req.Nome
	response.Codigo = req.Codigo

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil
}

func DeleteCurso(cursoId string) error {
	get, err := GetCursoById(cursoId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
