package models

import (
	"gorm.io/gorm"
)

type Turno struct {
	gorm.Model
	TurnoId    string `gorm:"index:idx_turno_id,unique"`
	TurnoNome  string `json:"turno_nome"`
	TurnoSigla string `gorm:"unique" json:"turno_sigla"`
}

type TurnoRequestModel struct {
	TurnoNome  string `json:"turno_nome" binding:"required"`
	TurnoSigla string `json:"turno_sigla" binding:"required"`
}

type TurnoResponseModel struct {
	TurnoId    string `json:"turno_id"`
	TurnoNome  string `json:"turno_nome"`
	TurnoSigla string `json:"turno_sigla"`
}

func (Turno) TableName() string {
	return "tb_turno"
}
