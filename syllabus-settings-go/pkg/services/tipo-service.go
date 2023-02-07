package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func CreateTipo(req *models.Tipo) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetTipoById(tipoId string) (*models.Tipo, error) {
	var tipo models.Tipo

	models.DB.First(&tipo, "tipo_id", tipoId)

	if tipo.ID == 0 {
		return &tipo, errors.New("tipo not found")
	}

	return &tipo, nil
}

func GetTipoByNome(tipoNome string) (*models.Tipo, error) {
	var tipo models.Tipo

	models.DB.First(&tipo, "tipo_nome", tipoNome)

	if tipo.ID == 0 {
		return &tipo, errors.New("tipo not found")
	}

	return &tipo, nil
}

func GetTipos() (*[]models.Tipo, error) {

	var tipos []models.Tipo

	result := models.DB.Find(&tipos)

	if result.Error != nil {
		return &tipos, result.Error
	}

	return &tipos, nil
}

func UpdateTipo(tipoId string, req *models.Tipo) (*models.Tipo, error) {
	response, err := GetTipoById(tipoId)

	if err != nil {
		return req, err
	}

	response.TipoNome = req.TipoNome
	response.TipoValor = req.TipoValor

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteTipo(tipoId string) error {
	get, err := GetTipoById(tipoId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
