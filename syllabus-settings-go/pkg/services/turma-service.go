package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"gorm.io/gorm/clause"
)

func CreateCourseType(req *models.CourseType) error {

	if req.Disciplina.ID == 0 || req.Turno.ID == 0 {
		return errors.New("failed to create turma")
	}

	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetCourseTypeById(turmaId string, preload ...string) (*models.CourseType, error) {
	var turma models.CourseType

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&turma, "turma_id", turmaId)

	if turma.ID == 0 {
		return &turma, errors.New("turma not found")
	}

	return &turma, nil
}

func GetCourseTypeByCodigo(codigo string, preload ...string) (*models.CourseType, error) {
	var turma models.CourseType

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&turma, "codigo", codigo)

	if turma.ID == 0 {
		return &turma, errors.New("turma not found")
	}

	return &turma, nil
}

func GetCourseTypeByIdOrCodigo(turma string, preload ...string) (*models.CourseType, error) {

	if utils.IsValidUUID(turma) {
		return GetCourseTypeById(turma, preload...)
	}

	return GetCourseTypeByCodigo(turma)
}

func GetCourseTypes() (*[]models.CourseType, error) {

	var turmas []models.CourseType

	result := models.DB.Preload(clause.Associations).Find(&turmas)

	if result.Error != nil {
		return &turmas, result.Error
	}

	return &turmas, nil
}

func GetCourseTypesByDisciplinaId(disciplinaId uint) (*[]models.CourseType, error) {
	var turmas []models.CourseType

	result := models.DB.Preload(clause.Associations).Find(&turmas, "disciplina_id", disciplinaId)

	if result.Error != nil {
		return &turmas, result.Error
	}

	return &turmas, nil
}

func UpdateCourseType(turma string, req *models.CourseType) (*models.CourseType, error) {

	if req.Disciplina.ID == 0 || req.Turno.ID == 0 {
		return &models.CourseType{}, errors.New("failed to update turma")
	}

	response, err := GetCourseTypeByIdOrCodigo(turma)

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

func DeleteCourseType(turma string) error {
	get, err := GetCourseTypeByIdOrCodigo(turma)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
