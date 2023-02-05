package models

import (
	"gorm.io/gorm"
)

type CursoEntity struct {
	gorm.Model
	CursoId     string            `gorm:"" json:"curso_id"`
	Nome        string            `json:"nome"`
	Codigo      string            `json:"codigo"`
	Periodos    int16             `json:"periodos"`
	Disciplinas []DisciplinaCurso `gorm:"foreignKey:CursoID;references:ID" json:"disciplinas"`
}

type CursoRequestModel struct {
	Nome     string `json:"nome"`
	Codigo   string `json:"codigo"`
	Periodos int16  `json:"periodos"`
}

type CursoResponseModel struct {
	CursoId  string `json:"curso_id"`
	Codigo   string `json:"codigo"`
	Nome     string `json:"nome"`
	Periodos int16  `json:"periodos"`
}

/*type CursoDisciplinasResponseModel struct {
	CursoId     string                        `json:"curso_id"`
	Codigo      string                        `json:"codigo"`
	Nome        string                        `json:"nome"`
	Disciplinas []DisciplinaCursoRequestModel `json:"disciplinas"`
}*/

func (CursoEntity) TableName() string {
	return "tb_curso"
}
