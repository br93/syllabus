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

func GetClassScheduleById(horarioaulaId string) (*models.ClassSchedule, error) {
	var horarioaula models.ClassSchedule

	models.DB.Preload(clause.Associations).First(&horarioaula, "horarioaula_id", horarioaulaId)

	if horarioaula.ID == 0 {
		return &horarioaula, errors.New("horarioaula not found")
	}

	return &horarioaula, nil
}

func GetClassSchedules() (*[]models.ClassSchedule, error) {

	var horarioaulas []models.ClassSchedule

	result := models.DB.Preload(clause.Associations).Find(&horarioaulas)

	if result.Error != nil {
		return &horarioaulas, result.Error
	}

	return &horarioaulas, nil
}

func UpdateClassSchedule(horarioaulaId string, req *models.ClassSchedule) (*models.ClassSchedule, error) {
	response, err := GetClassScheduleById(horarioaulaId)

	if err != nil {
		return req, err
	}

	response.Turma = req.Turma
	response.Dia = req.Dia
	response.Horario = req.Horario

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteClassSchedule(horarioaulaId string) error {
	get, err := GetClassScheduleById(horarioaulaId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
