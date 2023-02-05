package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func CreateTurno(req *models.TurnoEntity) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetTurnoById(turnoId string) (*models.TurnoEntity, error) {
	var turno models.TurnoEntity

	models.DB.First(&turno, "turno_id = ?", turnoId)

	if turno.ID == 0 {
		return &turno, errors.New("turno not found")
	}

	return &turno, nil
}

func GetTurnos() (*[]models.TurnoEntity, error) {

	var turnos []models.TurnoEntity

	result := models.DB.Find(&turnos)

	if result.Error != nil {
		return &turnos, result.Error
	}

	return &turnos, nil
}

func UpdateTurno(turnoId string, req *models.TurnoEntity) (*models.TurnoEntity, error) {
	response, err := GetTurnoById(turnoId)

	if err != nil {
		return req, err
	}

	response.TurnoNome = req.TurnoNome
	response.TurnoValor = req.TurnoValor

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteTurno(turnoId string) error {
	get, err := GetTurnoById(turnoId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
