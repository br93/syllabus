package models

import (
	"gorm.io/gorm"
)

type Turno struct {
	gorm.Model
	TurnoId    string `gorm:"index:idx_turno_id,unique"`
	TurnoNome  string `gorm:"unique" json:"turno_nome"`
	TurnoValor int16  `json:"turno_valor"`
}

type TurnoRequestModel struct {
	TurnoNome  string `json:"turno_nome" binding:"required"`
	TurnoValor int16  `json:"turno_valor" binding:"required,gte=1,lte=10"`
}

type TurnoResponseModel struct {
	TurnoId   string `json:"turno_id"`
	TurnoNome string `json:"turno_nome"`
}

func (Turno) TableName() string {
	return "tb_turno"
}
