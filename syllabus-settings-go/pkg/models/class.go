package models

import (
	"gorm.io/gorm"
)

type Class struct {
	gorm.Model
	ClassId        string          `gorm:"index:idx_class_id,unique"`
	ClassCode      string          `gorm:"unique" json:"class_code"`
	Course         Course          `json:"course"`
	ClassSchedules []ClassSchedule `gorm:"foreignKey:ClassID;references:ID" json:"class_hours"`
	CourseID       uint            `json:"course_id"`
}

type ClassRequestModel struct {
	ClassCode string `json:"class_code" binding:"required,min=3,max=10"`
	Course    string `json:"course" binding:"required,min=3,max=10"`
}

type ClassResponseModel struct {
	ClassId   string `json:"class_id"`
	ClassCode string `json:"class_code"`
}

type ClassSchedulesResponseModel struct {
	ClassId        string                       `json:"class_id"`
	Course         string                       `json:"course"`
	ClassSchedules []ClassScheduleResponseModel `json:"class_hours"`
}

func (Class) TableName() string {
	return "tb_classes"
}
