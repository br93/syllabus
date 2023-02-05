package models

import (
	"gorm.io/gorm"
)

type DisciplinaCurso struct {
	gorm.Model
	DisciplinaCursoId string           `gorm:"" json:"disciplina_curso_id"`
	Curso             CursoEntity      `json:"curso"`
	Disciplina        DisciplinaEntity `json:"disciplina"`
	Tipo              Tipo             `json:"tipo"`
	Periodo           int16            `json:"periodo"`
	CursoID           uint             `json:"curso_id"`
	DisciplinaID      uint             `json:"disciplina_id"`
	TipoID            uint             `json:"tipo_id"`
}

type DisciplinaCursoRequestModel struct {
	Disciplina string `json:"codigo_disciplina"`
	Curso      string `json:"codigo_curso"`
	Tipo       string `json:"tipo_nome"`
	Periodo    int16  `json:"periodo"`
}

type DisciplinaCursoResponseModel struct {
	Disciplina string `json:"codigo_disciplina"`
	Tipo       string `json:"tipo_nome"`
	Periodo    int16  `json:"periodo"`
}
