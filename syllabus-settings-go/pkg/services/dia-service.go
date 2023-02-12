package services

import (
	"errors"
	"strconv"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
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

func GetDiaByIdOrNumero(dia string) (*models.Dia, error) {
	if utils.IsValidUUID(dia) {
		return GetDiaById(dia)
	}

	diaNumero, err := strconv.ParseInt(dia, 10, 16)

	if err != nil {
		return &models.Dia{}, errors.New("dia not found")
	}

	return GetDiaByNumero(int16(diaNumero))
}

func GetDias() (*[]models.Dia, error) {
	var dias []models.Dia

	result := models.DB.Find(&dias)

	if result.Error != nil {
		return &dias, result.Error
	}

	return &dias, nil
}

func UpdateDia(dia string, req *models.Dia) (*models.Dia, error) {
	response, err := GetDiaByIdOrNumero(dia)

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

func DeleteDia(dia string) error {
	get, err := GetDiaByIdOrNumero(dia)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
