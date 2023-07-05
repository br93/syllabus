package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateSchedule(req *models.Schedule) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetScheduleById(horarioId string) (*models.Schedule, error) {
	var horario models.Schedule

	models.DB.First(&horario, "horario_id", horarioId)

	if horario.ID == 0 {
		return &horario, errors.New("horario not found")
	}

	return &horario, nil
}

func GetScheduleBySigla(sigla string) (*models.Schedule, error) {
	var horario models.Schedule

	models.DB.First(&horario, "sigla", sigla)

	if horario.ID == 0 {
		return &horario, errors.New("horario not found")
	}

	return &horario, nil
}

func GetScheduleByIdOrSigla(horario string) (*models.Schedule, error) {
	if utils.IsValidUUID(horario) {
		return GetScheduleById(horario)
	}

	return GetScheduleBySigla(horario)
}

func GetSchedules() (*[]models.Schedule, error) {

	var horarios []models.Schedule

	result := models.DB.Find(&horarios)

	if result.Error != nil {
		return &horarios, result.Error
	}

	return &horarios, nil
}

func UpdateSchedule(horario string, req *models.Schedule) (*models.Schedule, error) {
	response, err := GetScheduleByIdOrSigla(horario)

	if err != nil {
		return req, err
	}

	response.Sigla = req.Sigla
	response.Faixa = req.Faixa

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteSchedule(horario string) error {
	get, err := GetScheduleByIdOrSigla(horario)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
