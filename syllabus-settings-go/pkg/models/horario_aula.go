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
	Turma   string `json:"codigo_turma"`
	Dia     int16  `json:"dia_numero"`
	Horario string `json:"horario_sigla"`
}

type HorarioAulaResponseModel struct {
	HorarioAulaId string `json:"horario_aula_id"`
	Turma         string `json:"codigo_turma"`
	Horario       string `json:"horario"`
}

func (HorarioAula) TableName() string {
	return "tb_horario_aula"
}
