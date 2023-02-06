package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"gorm.io/gorm/clause"
)

func CreateHorarioAula(req *models.HorarioAula) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetHorarioAulaById(horarioaulaId string) (*models.HorarioAula, error) {
	var horarioaula models.HorarioAula

	models.DB.Preload(clause.Associations).First(&horarioaula, "horarioaula_id", horarioaulaId)

	if horarioaula.ID == 0 {
		return &horarioaula, errors.New("horarioaula not found")
	}

	return &horarioaula, nil
}

func GetHorarioAulas() (*[]models.HorarioAula, error) {

	var horarioaulas []models.HorarioAula

	result := models.DB.Preload(clause.Associations).Find(&horarioaulas)

	if result.Error != nil {
		return &horarioaulas, result.Error
	}

	return &horarioaulas, nil
}

func UpdateHorarioAula(horarioaulaId string, req *models.HorarioAula) (*models.HorarioAula, error) {
	response, err := GetHorarioAulaById(horarioaulaId)

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

func DeleteHorarioAula(horarioaulaId string) error {
	get, err := GetHorarioAulaById(horarioaulaId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
