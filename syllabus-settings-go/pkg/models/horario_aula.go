package models

import (
	"gorm.io/gorm"
)

type HorarioAula struct {
	gorm.Model
	HorarioAulaId string  `gorm:"index:horario_aula_id,unique"`
	Turma         Turma   `json:"turma"`
	Dia           Dia     `json:"dia"`
	Horario       Horario `json:"horario"`
	TurmaID       uint    `json:"turma_id"`
	DiaID         uint    `json:"dia_id"`
	HorarioID     uint    `json:"horario_id"`
}

type HorarioAulaRequestModel struct {
	Turma   string `json:"codigo_turma" binding:"required,min=3,max=10"`
	Dia     int16  `json:"dia_numero" binding:"required,gte=2,lte=7"`
	Horario string `json:"horario_sigla" binding:"len=2"`
}

type HorarioAulaResponseModel struct {
	HorarioAulaId string `json:"horario_aula_id"`
	Turma         string `json:"codigo_turma"`
	Horario       string `json:"horario"`
}

func (HorarioAula) TableName() string {
	return "tb_horario_aula"
}
