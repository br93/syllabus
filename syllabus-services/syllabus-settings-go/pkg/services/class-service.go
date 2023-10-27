package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"gorm.io/gorm/clause"
)

func CreateClass(req *models.Class) error {

	if req.Course.ID == 0 {
		return errors.New("failed to create class")
	}

	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetClassById(classId string, preload ...string) (*models.Class, error) {
	var class models.Class

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&class, "class_id", classId)

	if class.ID == 0 {
		return &class, errors.New("class not found")
	}

	return &class, nil
}

func GetClassByCode(code string, preload ...string) (*models.Class, error) {
	var class models.Class

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&class, "class_code", code)

	if class.ID == 0 {
		return &class, errors.New("class not found")
	}

	return &class, nil
}

func GetClassByIdOrCode(class string, preload ...string) (*models.Class, error) {

	if utils.IsValidUUID(class) {
		return GetClassById(class, preload...)
	}

	return GetClassByCode(class)
}

func GetClasses() (*[]models.Class, error) {

	var classes []models.Class

	result := models.DB.Preload(clause.Associations).Find(&classes)

	if result.Error != nil {
		return &classes, result.Error
	}

	return &classes, nil
}

func GetClassesByCourseId(courseId uint) (*[]models.Class, error) {
	var classes []models.Class

	result := models.DB.Preload(clause.Associations).Find(&classes, "course_id", courseId)

	if result.Error != nil {
		return &classes, result.Error
	}

	return &classes, nil
}

func UpdateClass(class string, req *models.Class) (*models.Class, error) {

	if req.Course.ID == 0 {
		return &models.Class{}, errors.New("failed to update class")
	}

	response, err := GetClassByIdOrCode(class)

	if err != nil {
		return req, err
	}

	response.ClassCode = req.ClassCode
	response.Course = req.Course

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteClass(class string) error {
	get, err := GetClassByIdOrCode(class)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
