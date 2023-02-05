package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func CreateDisciplina(req *models.DisciplinaEntity) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDisciplinaById(disciplinaId string) (*models.DisciplinaEntity, error) {
	var disciplina models.DisciplinaEntity

	models.DB.First(&disciplina, "disciplina_id = ?", disciplinaId)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinaByCodigo(codigo string) (*models.DisciplinaEntity, error) {
	var disciplina models.DisciplinaEntity

	models.DB.First(&disciplina, "codigo = ?", codigo)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinas() (*[]models.DisciplinaEntity, error) {

	var disciplinas []models.DisciplinaEntity

	result := models.DB.Find(&disciplinas)

	if result.Error != nil {
		return &disciplinas, result.Error
	}

	return &disciplinas, nil
}

func UpdateDisciplina(disciplinaId string, req *models.DisciplinaEntity) (*models.DisciplinaEntity, error) {
	response, err := GetDisciplinaById(disciplinaId)

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

func CreatePreRequisito(disciplinaId string, req *[]models.DisciplinaEntity) (*models.DisciplinaEntity, error) {
	disciplina, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return &models.DisciplinaEntity{}, err
	}

	disciplina.PreRequisitos = req

	update := models.DB.Save(disciplina)

	if update.Error != nil {
		return &models.DisciplinaEntity{}, update.Error
	}

	return disciplina, nil
}

func DeleteDisciplina(disciplinaId string) error {
	get, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
