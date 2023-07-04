package models

import (
	"gorm.io/gorm"
)

type Schedule struct {
	gorm.Model
	ScheduleId   string `gorm:"index:schedule_id,unique"`
	ScheduleCode string `gorm:"unique" json:"schedule_code"`
	TimeRange    string `json:"time_range"`
}

type ScheduleRequestModel struct {
	ScheduleCode string `json:"schedule_code" binding:"required,len=2"`
	TimeRange    string `json:"time_range" binding:"required, len=20"`
}

type ScheduleResponseModel struct {
	ScheduleId   string `json:"schedule_id"`
	ScheduleCode string `json:"schedule_code"`
	TimeRange    string `json:"time_range"`
}

func (Schedule) TableName() string {
	return "tb_schedules"
}
