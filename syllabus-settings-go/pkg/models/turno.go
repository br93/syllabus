package models

import (
	"gorm.io/gorm"
)

type Turno struct {
	gorm.Model
	TurnoId    string `gorm:"" json:"turno_id"`
	TurnoNome  string `json:"turno_nome"`
	TurnoValor int16  `json:"turno_valor"`
}
