package models

import (
	"gorm.io/gorm"
)

type DisciplinaCurso struct {
	gorm.Model
	DisciplinaCursoId string     `gorm:"index:idx_disciplina_curso_id, unique"`
	Curso             Curso      `json:"curso"`
	Disciplina        Disciplina `json:"disciplina"`
	Tipo              Tipo       `json:"tipo"`
	Periodo           int16      `json:"periodo"`
	CursoID           uint       `json:"curso_id"`
	DisciplinaID      uint       `json:"disciplina_id"`
	TipoID            uint       `json:"tipo_id"`
}

type DisciplinaCursoRequestModel struct {
	Disciplina string `json:"codigo_disciplina" binding:"required,min=3,max=10"`
	Curso      string `json:"codigo_curso" binding:"required,min=3,max=10"`
	Tipo       string `json:"tipo_nome" binding:"required"`
	Periodo    int16  `json:"periodo" binding:"required,gte=1,lte=10"`
}

type DisciplinaCursoResponseModel struct {
	DisciplinaCursoId string `json:"disciplina_curso_id"`
	Disciplina        string `json:"codigo_disciplina"`
	Tipo              string `json:"tipo_nome"`
	Periodo           int16  `json:"periodo"`
}

func (DisciplinaCurso) TableNome() string {
	return "tb_disciplina_curso"
}
