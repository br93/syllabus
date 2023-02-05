package models

import (
	"gorm.io/gorm"
)

type DiaEntity struct {
	gorm.Model
	DiaId     string `gorm:"" json:"dia_id"`
	DiaNome   string `json:"dia_nome"`
	DiaNumero int16  `json:"dia_numero"`
}

type DiaRequestModel struct {
	DiaNome   string `json:"dia_nome"`
	DiaNumero int16  `json:"dia_numero"`
}

type DiaResponseModel struct {
	DiaId     string `json:"dia_id"`
	DiaNome   string `json:"dia_nome"`
	DiaNumero int16  `json:"dia_numero"`
}

func (DiaEntity) TableName() string {
	return "tb_dia"
}
