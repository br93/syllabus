package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"gorm.io/gorm"
)

func CreateDisciplina(req *models.Disciplina) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDisciplinaById(disciplinaId string, preload ...string) (*models.Disciplina, error) {
	var disciplina models.Disciplina

	db(models.DB, preload).First(&disciplina, "disciplina_id", disciplinaId)
	//eagerLoading(models.DB, preload)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinaByCodigo(codigo string) (*models.Disciplina, error) {
	var disciplina models.Disciplina

	models.DB.First(&disciplina, "codigo", codigo)

	if disciplina.ID == 0 {
		return &disciplina, errors.New("disciplina not found")
	}

	return &disciplina, nil
}

func GetDisciplinas() (*[]models.Disciplina, error) {

	var disciplinas []models.Disciplina

	result := models.DB.Find(&disciplinas)

	if result.Error != nil {
		return &disciplinas, result.Error
	}

	return &disciplinas, nil
}

func UpdateDisciplina(disciplinaId string, req *models.Disciplina) (*models.Disciplina, error) {
	response, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return req, err
	}

	response.Nome = req.Nome
	response.Codigo = req.Codigo
	response.CargaHoraria = req.CargaHoraria

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func CreatePreRequisito(disciplinaId string, req *[]models.Disciplina) (*models.Disciplina, error) {
	disciplina, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return &models.Disciplina{}, err
	}

	disciplina.PreRequisitos = req

	update := models.DB.Save(disciplina)

	if update.Error != nil {
		return &models.Disciplina{}, update.Error
	}

	return disciplina, nil
}

func CreateEquivalente(disciplinaId string, req *[]models.Disciplina) (*models.Disciplina, error) {
	disciplina, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return &models.Disciplina{}, err
	}

	disciplina.Equivalentes = req

	update := models.DB.Save(disciplina)

	if update.Error != nil {
		return &models.Disciplina{}, update.Error
	}

	return disciplina, nil
}

func DeleteDisciplina(disciplinaId string) error {
	get, err := GetDisciplinaById(disciplinaId)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}

func db(db *gorm.DB, preload []string) *gorm.DB {
	if preload == nil {
		return db
	}

	if len(preload) == 1 {
		eagerLoading := eagerLoading(db, preload)
		return eagerLoading
	}

	return eagerLoading(eagerLoading(db, preload), preload)

}

func eagerLoading(db *gorm.DB, preload []string) *gorm.DB {
	return db.Preload(preload[len(preload)-1])
}
