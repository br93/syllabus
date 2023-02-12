package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"gorm.io/gorm/clause"
)

func CreateTurma(req *models.Turma) error {

	if req.Disciplina.ID == 0 || req.Turno.ID == 0 {
		return errors.New("failed to create turma")
	}

	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetTurmaById(turmaId string, preload ...string) (*models.Turma, error) {
	var turma models.Turma

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&turma, "turma_id", turmaId)

	if turma.ID == 0 {
		return &turma, errors.New("turma not found")
	}

	return &turma, nil
}

func GetTurmaByCodigo(codigo string, preload ...string) (*models.Turma, error) {
	var turma models.Turma

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&turma, "codigo", codigo)

	if turma.ID == 0 {
		return &turma, errors.New("turma not found")
	}

	return &turma, nil
}

func GetTurmaByIdOrCodigo(turma string, preload ...string) (*models.Turma, error) {

	if utils.IsValidUUID(turma) {
		return GetTurmaById(turma, preload...)
	}

	return GetTurmaByCodigo(turma)
}

func GetTurmas() (*[]models.Turma, error) {

	var turmas []models.Turma

	result := models.DB.Preload(clause.Associations).Find(&turmas)

	if result.Error != nil {
		return &turmas, result.Error
	}

	return &turmas, nil
}

func GetTurmasByDisciplinaId(disciplinaId uint) (*[]models.Turma, error) {
	var turmas []models.Turma

	result := models.DB.Preload(clause.Associations).Find(&turmas, "disciplina_id", disciplinaId)

	if result.Error != nil {
		return &turmas, result.Error
	}

	return &turmas, nil
}

func UpdateTurma(turma string, req *models.Turma) (*models.Turma, error) {

	if req.Disciplina.ID == 0 || req.Turno.ID == 0 {
		return &models.Turma{}, errors.New("failed to update turma")
	}

	response, err := GetTurmaByIdOrCodigo(turma)

	if err != nil {
		return req, err
	}

	response.Codigo = req.Codigo
	response.Turno = req.Turno
	response.Disciplina = req.Disciplina

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteTurma(turma string) error {
	get, err := GetTurmaByIdOrCodigo(turma)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
