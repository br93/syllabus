package models

import (
	"gorm.io/gorm"
)

type Day struct {
	gorm.Model
	DayId     string `gorm:"index:idx_day_id,unique"`
	DayName   string `json:"day_name"`
	DayNumber int16  `gorm:"unique" json:"day_number"`
}

type DayRequestModel struct {
	DayName   string `json:"day_name" binding:"required,max=50"`
	DayNumber int16  `json:"day_number" binding:"required,gte=2,lte=7"`
}

type DayResponseModel struct {
	DayId     string `json:"day_id"`
	DayName   string `json:"day_name"`
	DayNumber int16  `json:"day_number"`
}

func (Day) TableName() string {
	return "tb_days"
}
