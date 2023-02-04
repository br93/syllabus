package models

import (
	"gorm.io/gorm"
)

type Turma struct {
	gorm.Model
	TurmaId      string        `gorm:"" json:"turma_id"`
	Codigo       string        `json:"codigo"`
	Turno        TurnoEntity   `json:"turno"`
	Disciplina   Disciplina    `json:"disciplina"`
	HorariosAula []HorarioAula `json:"horarios_aula"`
	TurnoID      uint          `json:"turno_id"`
	DisciplinaID uint          `json:"disciplina_id"`
}
