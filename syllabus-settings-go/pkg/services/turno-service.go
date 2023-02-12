package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateTurno(req *models.Turno) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetTurnoById(turnoId string) (*models.Turno, error) {
	var turno models.Turno

	models.DB.First(&turno, "turno_id", turnoId)

	if turno.ID == 0 {
		return &turno, errors.New("turno not found")
	}

	return &turno, nil
}

func GetTurnoByNome(turnoNome string) (*models.Turno, error) {
	var turno models.Turno

	models.DB.First(&turno, "turno_nome", turnoNome)

	if turno.ID == 0 {
		return &turno, errors.New("turno not found")
	}

	return &turno, nil
}

func GetTurnoByIdOrNome(turno string) (*models.Turno, error) {
	if utils.IsValidUUID(turno) {
		return GetTurnoById(turno)
	}

	return GetTurnoByNome(turno)
}

func GetTurnos() (*[]models.Turno, error) {

	var turnos []models.Turno

	result := models.DB.Find(&turnos)

	if result.Error != nil {
		return &turnos, result.Error
	}

	return &turnos, nil
}

func UpdateTurno(turno string, req *models.Turno) (*models.Turno, error) {
	response, err := GetTurnoByIdOrNome(turno)

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

func DeleteTurno(turno string) error {
	get, err := GetTurnoByIdOrNome(turno)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
