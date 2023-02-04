package models

import (
	"gorm.io/gorm"
)

type TurnoEntity struct {
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

func (TurnoEntity) TableName() string {
	return "tb_turno"
}
