package models

import (
	"gorm.io/gorm"
)

type Horario struct {
	gorm.Model
	HorarioId string `gorm:"" json:"horario_id"`
	Sigla     string `json:"sigla"`
	Faixa     string `json:"faixa"`
}
