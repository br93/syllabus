package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"gorm.io/gorm/clause"
)

func CreateClassSchedule(req *models.ClassSchedule) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetClassScheduleById(classScheduleId string) (*models.ClassSchedule, error) {
	var classSchedule models.ClassSchedule

	models.DB.Preload(clause.Associations).First(&classSchedule, "class_schedule_id", classScheduleId)

	if classSchedule.ID == 0 {
		return &classSchedule, errors.New("class schedule not found")
	}

	return &classSchedule, nil
}

func GetClassSchedules() (*[]models.ClassSchedule, error) {

	var classSchedules []models.ClassSchedule

	result := models.DB.Preload(clause.Associations).Find(&classSchedules)

	if result.Error != nil {
		return &classSchedules, result.Error
	}

	return &classSchedules, nil
}

func UpdateClassSchedule(classScheduleId string, req *models.ClassSchedule) (*models.ClassSchedule, error) {
	response, err := GetClassScheduleById(classScheduleId)

	if err != nil {
		return req, err
	}

	response.Class = req.Class
	response.Day = req.Day
	response.Schedule = req.Schedule

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteClassSchedule(classScheduleId string) error {
	get, err := GetClassScheduleById(classScheduleId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
