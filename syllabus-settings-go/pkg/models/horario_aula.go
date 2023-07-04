package models

import (
	"gorm.io/gorm"
)

type ClassSchedule struct {
	gorm.Model
	ClassScheduleId string   `gorm:"index:class_schedule_id,unique"`
	Class           Class    `json:"class"`
	Day             Day      `json:"day"`
	Schedule        Schedule `json:"schedule"`
	ClassID         uint     `json:"class_id"`
	DayID           uint     `json:"day_id"`
	ScheduleID      uint     `json:"schedule_id"`
}

type ClassScheduleRequestModel struct {
	ClassCode    string `json:"class_code" binding:"required,min=3,max=10"`
	DayNumber    int16  `json:"day_number" binding:"required,gte=2,lte=7"`
	ScheduleCode string `json:"schedule_code" binding:"len=2"`
}

type ClassScheduleResponseModel struct {
	ClassScheduleId string `json:"class_schedule_id"`
	ClassCode       string `json:"class_code"`
	Schedule        string `json:"schedule"`
}

func (ClassSchedule) TableName() string {
	return "tb_class_schedules"
}
