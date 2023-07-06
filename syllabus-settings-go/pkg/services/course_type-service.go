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

func GetCourseTypeById(courseTypeId string) (*models.CourseType, error) {
	var courseType models.CourseType

	models.DB.First(&courseType, "type_id", courseTypeId)

	if courseType.ID == 0 {
		return &courseType, errors.New("course type not found")
	}

	return &courseType, nil
}

func GetCourseTypeByName(courseTypeName string) (*models.CourseType, error) {
	var courseType models.CourseType

	models.DB.First(&courseType, "type_name", courseTypeName)

	if courseType.ID == 0 {
		return &courseType, errors.New("course type not found")
	}

	return &courseType, nil
}

func GetCourseTypeByIdOrName(courseTypeId string) (*models.CourseType, error) {
	if utils.IsValidUUID(courseTypeId) {
		return GetCourseTypeById(courseTypeId)
	}

	courseTypeName := courseTypeId
	return GetCourseTypeByName(courseTypeName)
}

func GetCourseTypes() (*[]models.CourseType, error) {

	var courseTypes []models.CourseType

	result := models.DB.Find(&courseTypes)

	if result.Error != nil {
		return &courseTypes, result.Error
	}

	return &courseTypes, nil
}

func UpdateCourseType(courseType string, req *models.CourseType) (*models.CourseType, error) {
	response, err := GetCourseTypeByIdOrName(courseType)

	if err != nil {
		return req, err
	}

	response.TypeName = req.TypeName
	response.TypeValue = req.TypeValue

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteCourseType(courseType string) error {
	get, err := GetCourseTypeByIdOrName(courseType)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
