package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"gorm.io/gorm/clause"
)

func CreateUniversity(req *models.University) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetUniversityById(universityId string, preload ...string) (*models.University, error) {
	var university models.University

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&university, "university_id", universityId)

	if university.ID == 0 {
		return &university, errors.New("university not found")
	}

	return &university, nil
}

func GetUniversityByCode(code string, preload ...string) (*models.University, error) {
	var university models.University

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&university, "university_code", code)

	if university.ID == 0 {
		return &university, errors.New("university not found")
	}

	return &university, nil
}

func GetUniversityByIdOrCode(university string, preload ...string) (*models.University, error) {
	if utils.IsValidUUID(university) {
		return GetUniversityById(university, preload...)
	}

	return GetUniversityByCode(university, preload...)
}

func GetUniversities() (*[]models.University, error) {
	var universities []models.University

	result := models.DB.Find(&universities)

	if result.Error != nil {
		return &universities, result.Error
	}

	return &universities, nil
}

func UpdateUniversity(university string, req *models.University) (*models.University, error) {
	response, err := GetUniversityByIdOrCode(university)

	if err != nil {
		return req, err
	}

	response.UniversityName = req.UniversityName
	response.UniversityCode = req.UniversityCode

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil
}

func DeleteUniversity(university string) error {
	get, err := GetUniversityByIdOrCode(university)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
