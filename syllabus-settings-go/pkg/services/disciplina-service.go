package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateDisciplina(req *models.Disciplina) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDisciplinaById(disciplinaId string, preload ...string) (*models.Disciplina, error) {
	var disciplina models.Disciplina

	models.DBConfig(models.DB, preload).First(&disciplina, "disciplina_id", disciplinaId)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinaByCodigo(codigo string, preload ...string) (*models.Disciplina, error) {
	var disciplina models.Disciplina

	models.DBConfig(models.DB, preload).First(&disciplina, "codigo", codigo)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinaByIdOrCodigo(disciplina string, preload ...string) (*models.Disciplina, error) {
	if utils.IsValidUUID(disciplina) {
		return GetDisciplinaById(disciplina, preload...)
	}

	return GetDisciplinaByCodigo(disciplina, preload...)
}

func GetDisciplinas() (*[]models.Disciplina, error) {

	var disciplinas []models.Disciplina

	result := models.DB.Find(&disciplinas)

	if result.Error != nil {
		return &disciplinas, result.Error
	}

	return &disciplinas, nil
}

func UpdateDisciplina(disciplina string, req *models.Disciplina) (*models.Disciplina, error) {
	response, err := GetDisciplinaByIdOrCodigo(disciplina)

	if err != nil {
		return req, err
	}

	response.Nome = req.Nome
	response.Codigo = req.Codigo
	response.CargaHoraria = req.CargaHoraria

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func CreatePreRequisito(d string, req *[]models.Disciplina) (*models.Disciplina, error) {
	disciplina, err := GetDisciplinaByIdOrCodigo(d)

	if err != nil {
		return &models.Disciplina{}, err
	}

	disciplina.PreRequisitos = req

	update := models.DB.Save(disciplina)

	if update.Error != nil {
		return &models.Disciplina{}, update.Error
	}

	return disciplina, nil
}

func CreateEquivalente(d string, req *[]models.Disciplina) (*models.Disciplina, error) {
	disciplina, err := GetDisciplinaByIdOrCodigo(d)

	if err != nil {
		return &models.Disciplina{}, err
	}

	disciplina.Equivalentes = req

	update := models.DB.Save(disciplina)

	if update.Error != nil {
		return &models.Disciplina{}, update.Error
	}

	return disciplina, nil
}

func DeleteDisciplina(disciplina string) error {
	get, err := GetDisciplinaByIdOrCodigo(disciplina)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
