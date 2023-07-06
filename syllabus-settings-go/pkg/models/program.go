package models

import (
	"gorm.io/gorm"
)

type Program struct {
	gorm.Model
	ProgramId    string          `gorm:"index:idx_program_id, unique"`
	ProgramName  string          `json:"program_name"`
	ProgramCode  string          `gorm:"unique" json:"program_code"`
	Terms        int16           `json:"terms"`
	Courses      []CourseProgram `gorm:"foreignKey:ProgramID;references:ID" json:"courses"`
	University   University      `json:"university"`
	UniversityID uint            `json:"university_id"`
}

type ProgramRequestModel struct {
	ProgramName    string `json:"program_name" binding:"required,max=50"`
	ProgramCode    string `json:"program_code" binding:"required,min=3,max=10"`
	UniversityCode string `json:"university_code" binding:"required"`
	Terms          int16  `json:"terms" binding:"required,gte=1,lte=10"`
}

type ProgramResponseModel struct {
	ProgramId      string `json:"program_id"`
	ProgramCode    string `json:"program_code"`
	ProgramName    string `json:"program_name"`
	UniversityCode string `json:"university_code"`
	Terms          int16  `json:"terms"`
}

type CoursesResponseModel struct {
	ProgramId      string                       `json:"program_id"`
	ProgramCode    string                       `json:"program_code"`
	ProgramName    string                       `json:"program_name"`
	UniversityCode string                       `json:"university_code"`
	Courses        []CourseProgramResponseModel `json:"courses"`
}

func (Program) TableName() string {
	return "tb_programs"
}
