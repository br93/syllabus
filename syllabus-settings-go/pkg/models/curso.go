package models

import (
	"gorm.io/gorm"
)

type Curso struct {
	gorm.Model
	CursoId     string            `gorm:"" json:"curso_id"`
	Nome        string            `json:"nome"`
	Codigo      string            `json:"codigo"`
	Periodos    int16             `json:"periodos"`
	Disciplinas []DisciplinaCurso `json:"disciplinas"`
}
