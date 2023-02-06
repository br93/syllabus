package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func CreateHorario(req *models.Horario) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetHorarioById(horarioId string) (*models.Horario, error) {
	var horario models.Horario

	models.DB.First(&horario, "horario_id", horarioId)

	if horario.ID == 0 {
		return &horario, errors.New("horario not found")
	}

	return &horario, nil
}

func GetHorarioBySigla(sigla string) (*models.Horario, error) {
	var horario models.Horario

	models.DB.First(&horario, "sigla", sigla)

	if horario.ID == 0 {
		return &horario, errors.New("horario not found")
	}

	return &horario, nil
}

func GetHorarios() (*[]models.Horario, error) {

	var horarios []models.Horario

	result := models.DB.Find(&horarios)

	if result.Error != nil {
		return &horarios, result.Error
	}

	return &horarios, nil
}

func UpdateHorario(horarioId string, req *models.Horario) (*models.Horario, error) {
	response, err := GetHorarioById(horarioId)

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

func DeleteHorario(horarioId string) error {
	get, err := GetHorarioById(horarioId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
