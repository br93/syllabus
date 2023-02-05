package models

import (
	"gorm.io/gorm"
)

type DisciplinaEntity struct {
	gorm.Model
	DisciplinaId  string              `gorm:"" json:"disciplina_id"`
	Nome          string              `json:"nome"`
	Codigo        string              `json:"codigo"`
	CargaHoraria  int16               `json:"carga_horaria"`
	Turmas        []Turma             `gorm:"foreignKey:DisciplinaID;references:ID" json:"turmas"`
	Equivalentes  *[]DisciplinaEntity `gorm:"many2many:disciplinas_equivalentes"`
	PreRequisitos *[]DisciplinaEntity `gorm:"many2many:disciplinas_prerequisitos"`
	Cursos        []DisciplinaCurso   `gorm:"foreignKey:DisciplinaID;references:ID" json:"cursos"`
}

type DisciplinaRequestModel struct {
	Nome         string `json:"nome"`
	Codigo       string `json:"codigo"`
	CargaHoraria int16  `json:"carga_horaria"`
}

type DisciplinaResponseModel struct {
	DisciplinaId string `json:"disciplina_id"`
	Codigo       string `json:"codigo"`
	Nome         string `json:"nome"`
	CargaHoraria int16  `json:"carga_horaria"`
}

type DisciplinaCodigoRequestModel struct {
	Codigo string `json:"disciplina_codigo"`
}

type DisciplinaEquivalentesResponseModel struct {
	DisciplinaId string                    `json:"disciplina_id"`
	Codigo       string                    `json:"codigo"`
	Nome         string                    `json:"nome"`
	CargaHoraria int16                     `json:"carga_horaria"`
	Equivalentes []DisciplinaResponseModel `json:"disciplinas_equivalentes"`
}

type DisciplinaPreRequisitosResponseModel struct {
	DisciplinaId  string                    `json:"disciplina_id"`
	Codigo        string                    `json:"codigo"`
	Nome          string                    `json:"nome"`
	CargaHoraria  int16                     `json:"carga_horaria"`
	PreRequisitos []DisciplinaResponseModel `json:"disciplinas_prerequisitos"`
}

func (DisciplinaEntity) TableName() string {
	return "tb_disciplina"
}
