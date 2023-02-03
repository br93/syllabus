package models

import (
	"gorm.io/gorm"
)

type Dia struct {
	gorm.Model
	DiaId     string `gorm:"" json:"dia_id"`
	DiaNome   string `json:"dia_nome"`
	DiaNumero int16  `json:"dia_numero"`
}
