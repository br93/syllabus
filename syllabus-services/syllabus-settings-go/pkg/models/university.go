package models

import (
	"gorm.io/gorm"
)

type University struct {
	gorm.Model
	UniversityId   string    `gorm:"index:idx_university_id, unique"`
	UniversityName string    `json:"university_name"`
	UniversityCode string    `gorm:"unique" json:"university_code"`
	Programs       []Program `gorm:"foreignKey:UniversityID;references:ID" json:"programs"`
	Courses        []Course  `gorm:"foreignKey:UniversityID;references:ID" json:"courses"`
}

type UniversityRequestModel struct {
	UniversityName string `json:"university_name" binding:"required"`
	UniversityCode string `json:"university_code" binding:"required"`
}

type UniversityResponseModel struct {
	UniversityId   string `json:"university_id"`
	UniversityName string `json:"university_name"`
	UniversityCode string `json:"university_code"`
}

type UniversityProgramsResponseModel struct {
	UniversityId   string                 `json:"university_id"`
	UniversityName string                 `json:"university_name"`
	UniversityCode string                 `json:"university_code"`
	Programs       []ProgramResponseModel `json:"programs"`
}

type UniversityCoursesResponseModel struct {
	UniversityId   string                `json:"university_id"`
	UniversityName string                `json:"university_name"`
	UniversityCode string                `json:"university_code"`
	Courses        []CourseResponseModel `json:"courses"`
}

func (University) TableName() string {
	return "tb_universities"
}
