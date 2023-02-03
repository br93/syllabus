package models

import (
	"gorm.io/gorm"
)

type DisciplinaCurso struct {
	gorm.Model
	DisciplinaCursoId string     `gorm:"" json:"disciplina_curso_id"`
	Curso             Curso      `json:"curso"`
	Disciplina        Disciplina `json:"disciplina"`
	Tipo              Tipo       `json:"tipo"`
	Periodo           int16      `json:"periodo"`
	CursoID           uint       `json:"curso_id"`
	DisciplinaID      uint       `json:"disciplina_id"`
	TipoID            uint       `json:"tipo_id"`
}
