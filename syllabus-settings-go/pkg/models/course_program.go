package models

import (
	"gorm.io/gorm"
)

type CourseProgram struct {
	gorm.Model
	CourseProgramId string     `gorm:"index:idx_course_program_id, unique"`
	Program         Program    `json:"program"`
	Course          Course     `json:"course"`
	CourseType      CourseType `json:"course_type"`
	Term            int16      `json:"term"`
	ProgramID       uint       `json:"program_id"`
	CourseID        uint       `json:"course_id"`
	CourseTypeID    uint       `json:"course_type_id"`
}

type CourseProgramRequestModel struct {
	CourseCode  string `json:"course_code" binding:"required,min=3,max=10"`
	ProgramCode string `json:"program_code" binding:"required,min=3,max=10"`
	Type        string `json:"type" binding:"required"`
	Term        int16  `json:"term" binding:"required,gte=1,lte=10"`
}

type CourseProgramResponseModel struct {
	CourseProgramId string `json:"course_program_id"`
	CourseCode      string `json:"course_code"`
	CourseName      string `json:"course_name"`
	Type            string `json:"course_type"`
	Term            int16  `json:"term"`
}

func (CourseProgram) TableName() string {
	return "tb_course_programs"
}
