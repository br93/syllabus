package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"gorm.io/gorm/clause"
)

func CreateProgram(req *models.Program) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetProgramById(programId string, preload ...string) (*models.Program, error) {
	var program models.Program

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&program, "program_id", programId)

	if program.ID == 0 {
		return &program, errors.New("program not found")
	}

	return &program, nil
}

func GetProgramByCode(code string, preload ...string) (*models.Program, error) {
	var program models.Program

	models.DBConfig(models.DB.Preload(clause.Associations), preload).First(&program, "program_code", code)

	if program.ID == 0 {
		return &program, errors.New("program not found")
	}

	return &program, nil
}

func GetProgramByIdOrCode(program string, preload ...string) (*models.Program, error) {
	if utils.IsValidUUID(program) {
		return GetProgramById(program, preload...)
	}

	return GetProgramByCode(program, preload...)
}

func GetPrograms() (*[]models.Program, error) {
	var programs []models.Program

	result := models.DB.Preload(clause.Associations).Find(&programs)

	if result.Error != nil {
		return &programs, result.Error
	}

	return &programs, nil
}

func UpdateProgram(program string, req *models.Program) (*models.Program, error) {
	response, err := GetProgramByIdOrCode(program)

	if err != nil {
		return req, err
	}

	response.ProgramName = req.ProgramName
	response.ProgramCode = req.ProgramCode
	response.Terms = req.Terms

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil
}

func DeleteProgram(program string) error {
	get, err := GetProgramByIdOrCode(program)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
