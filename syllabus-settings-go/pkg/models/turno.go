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

type TurnoRequestModel struct {
	TurnoNome  string `json:"turno_nome"`
	TurnoValor int16  `json:"turno_valor"`
}

type TurnoResponseModel struct {
	TurnoId   string `json:"turno_id"`
	TurnoNome string `json:"turno_nome"`
}

func (Turno) TableName() string {
	return "tb_turno"
}
