package models

import (
	"gorm.io/gorm"
)

type HorarioAula struct {
	gorm.Model
	HorarioAulaId string    `gorm:"" json:"horario_aula_id"`
	Turma         Turma     `json:"turma"`
	Dia           DiaEntity `json:"dia"`
	Horario       Horario   `json:"horario"`
	TurmaID       uint      `json:"turma_id"`
	DiaID         uint      `json:"dia_id"`
	HorarioID     uint      `json:"horario_id"`
}
