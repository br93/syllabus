package models

import (
	"gorm.io/gorm"
)

type Curso struct {
	gorm.Model
	CursoId     string            `gorm:"index:idx_curso_id, unique"`
	Nome        string            `json:"nome"`
	Codigo      string            `gorm:"unique" json:"codigo"`
	Periodos    int16             `json:"periodos"`
	Disciplinas []DisciplinaCurso `gorm:"foreignKey:CursoID;references:ID" json:"disciplinas"`
}

type CursoRequestModel struct {
	Nome     string `json:"nome" binding:"required,max=50"`
	Codigo   string `json:"codigo" binding:"required,min=3,max=10"`
	Periodos int16  `json:"periodos" binding:"required,gte=1,lte=10"`
}

type CursoResponseModel struct {
	CursoId  string `json:"curso_id"`
	Codigo   string `json:"codigo"`
	Nome     string `json:"nome"`
	Periodos int16  `json:"periodos"`
}

type CursoDisciplinasResponseModel struct {
	CursoId     string                         `json:"curso_id"`
	Codigo      string                         `json:"codigo"`
	Nome        string                         `json:"nome"`
	Disciplinas []DisciplinaCursoResponseModel `json:"disciplinas"`
}

func (Curso) TableName() string {
	return "tb_curso"
}
