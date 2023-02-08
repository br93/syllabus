package models

import (
	"gorm.io/gorm"
)

type Disciplina struct {
	gorm.Model
	DisciplinaId  string            `gorm:"index:idx_disciplina_id, unique"`
	Nome          string            `json:"nome"`
	Codigo        string            `gorm:"unique" json:"codigo"`
	CargaHoraria  int16             `json:"carga_horaria"`
	Turmas        []Turma           `gorm:"foreignKey:DisciplinaID;references:ID" json:"turmas"`
	Equivalentes  *[]Disciplina     `gorm:"many2many:disciplinas_equivalentes"`
	PreRequisitos *[]Disciplina     `gorm:"many2many:disciplinas_prerequisitos"`
	Cursos        []DisciplinaCurso `gorm:"foreignKey:DisciplinaID;references:ID" json:"cursos"`
}

type DisciplinaRequestModel struct {
	Nome         string `json:"nome" binding:"required"`
	Codigo       string `json:"codigo" binding:"required,min=3,max=10"`
	CargaHoraria int16  `json:"carga_horaria" binding:"required,gte=10,lte=200"`
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

type DisciplinaTurmasResponseModel struct {
	DisciplinaId string               `json:"disciplina_id"`
	Codigo       string               `json:"codigo"`
	Nome         string               `json:"nome"`
	Turmas       []TurmaResponseModel `json:"turmas"`
}

func (Disciplina) TableName() string {
	return "tb_disciplina"
}
