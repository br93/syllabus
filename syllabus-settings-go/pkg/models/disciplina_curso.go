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
	Disciplina string `json:"codigo_disciplina"`
	Curso      string `json:"codigo_curso"`
	Tipo       string `json:"tipo_nome"`
	Periodo    int16  `json:"periodo"`
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
