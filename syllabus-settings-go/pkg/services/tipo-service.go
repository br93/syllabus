package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateCourseType(req *models.CourseType) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetCourseTypeById(tipoId string) (*models.CourseType, error) {
	var tipo models.CourseType

	models.DB.First(&tipo, "tipo_id", tipoId)

	if tipo.ID == 0 {
		return &tipo, errors.New("tipo not found")
	}

	return &tipo, nil
}

func GetCourseTypeByNome(tipoNome string) (*models.CourseType, error) {
	var tipo models.CourseType

	models.DB.First(&tipo, "tipo_nome", tipoNome)

	if tipo.ID == 0 {
		return &tipo, errors.New("tipo not found")
	}

	return &tipo, nil
}

func GetCourseTypeByIdOrNome(tipoId string) (*models.CourseType, error) {
	if utils.IsValidUUID(tipoId) {
		return GetCourseTypeById(tipoId)
	}

	tipoNome := tipoId
	return GetCourseTypeByNome(tipoNome)
}

func GetCourseTypes() (*[]models.CourseType, error) {

	var tipos []models.CourseType

	result := models.DB.Find(&tipos)

	if result.Error != nil {
		return &tipos, result.Error
	}

	return &tipos, nil
}

func UpdateCourseType(tipo string, req *models.CourseType) (*models.CourseType, error) {
	response, err := GetCourseTypeByIdOrNome(tipo)

	if err != nil {
		return req, err
	}

	response.CourseTypeNome = req.CourseTypeNome
	response.CourseTypeValor = req.CourseTypeValor

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteCourseType(tipo string) error {
	get, err := GetCourseTypeByIdOrNome(tipo)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
