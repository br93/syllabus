package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func CreateDia(req *models.Dia) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDiaById(diaId string) (*models.Dia, error) {
	var dia models.Dia

	models.DB.First(&dia, "dia_id", diaId)

	if dia.ID == 0 {
		return &dia, errors.New("dia not found")
	}

	return &dia, nil
}

func GetDiaByNumero(numero int16) (*models.Dia, error) {
	var dia models.Dia

	models.DB.First(&dia, "dia_numero", numero)

	if dia.ID == 0 {
		return &dia, errors.New("dia not found")
	}

	return &dia, nil
}

func GetDias() (*[]models.Dia, error) {
	var dias []models.Dia

	result := models.DB.Find(&dias)

	if result.Error != nil {
		return &dias, result.Error
	}

	return &dias, nil
}

func UpdateDia(diaId string, req *models.Dia) (*models.Dia, error) {
	response, err := GetDiaById(diaId)

	if err != nil {
		return req, err
	}

	response.DiaNome = req.DiaNome
	response.DiaNumero = req.DiaNumero

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil
}

func DeleteDia(diaId string) error {
	get, err := GetDiaById(diaId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
