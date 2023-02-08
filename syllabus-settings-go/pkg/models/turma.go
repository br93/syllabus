package models

import (
	"gorm.io/gorm"
)

type Turma struct {
	gorm.Model
	TurmaId      string        `gorm:"index:idx_turma_id,unique"`
	Codigo       string        `gorm:"unique" json:"codigo"`
	Turno        Turno         `json:"turno"`
	Disciplina   Disciplina    `json:"disciplina"`
	HorariosAula []HorarioAula `gorm:"foreignKey:TurmaID;references:ID" json:"horarios_aula"`
	TurnoID      uint          `json:"turno_id"`
	DisciplinaID uint          `json:"disciplina_id"`
}

type TurmaRequestModel struct {
	Codigo     string `json:"codigo" binding:"required,min=3,max=10"`
	Turno      string `json:"turno" binding:"required"`
	Disciplina string `json:"disciplina" binding:"required,min=3,max=10"`
}

type TurmaResponseModel struct {
	TurmaId string `json:"turma_id"`
	Codigo  string `json:"codigo"`
	Turno   string `json:"turno"`
}

type TurmaHorariosAulaResponseModel struct {
	TurmaId      string                     `json:"turma_id"`
	Disciplina   string                     `json:"disciplina"`
	Turno        string                     `json:"turno"`
	HorariosAula []HorarioAulaResponseModel `json:"horarios_aula"`
}

func (Turma) TableName() string {
	return "tb_turma"
}
