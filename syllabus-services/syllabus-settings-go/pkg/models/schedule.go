package models

import (
	"gorm.io/gorm"
)

type Schedule struct {
	gorm.Model
	ScheduleId   string `gorm:"index:idx_schedule_id,unique"`
	ScheduleCode string `gorm:"unique" json:"schedule_code"`
	TimeOfDay    string `json:"time_of_day"`
	TimeRange    string `json:"time_range"`
}

type ScheduleRequestModel struct {
	ScheduleCode string `json:"schedule_code" binding:"required,min=2,max=2`
	TimeOfDay    string `json:"time_of_day" binding:"required,min=1,max=1"`
	TimeRange    string `json:"time_range"`
}

type ScheduleResponseModel struct {
	ScheduleId   string `json:"schedule_id"`
	ScheduleCode string `json:"schedule_code"`
	TimeOfDay    string `json:"time_of_day"`
	TimeRange    string `json:"time_range"`
}

func (Schedule) TableName() string {
	return "tb_schedules"
}
