package models

import (
	"gorm.io/gorm"
)

type Disciplina struct {
	gorm.Model
	DisciplinaId  string            `gorm:"" json:"disciplina_id"`
	Nome          string            `json:"nome"`
	Codigo        string            `json:"codigo"`
	CargaHoraria  int16             `json:"carga_horaria"`
	Turmas        []Turma           `json:"turmas"`
	Equivalentes  []*Disciplina     `gorm:"many2many:disciplinas_equivalentes"`
	PreRequisitos []*Disciplina     `gorm:"many2many:disciplinas_prerequisitos"`
	Cursos        []DisciplinaCurso `json:"cursos"`
}
