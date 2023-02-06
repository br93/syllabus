package models

import (
	"gorm.io/gorm"
)

type Horario struct {
	gorm.Model
	HorarioId string `gorm:"" json:"horario_id"`
	Sigla     string `json:"sigla"`
	Faixa     string `json:"faixa"`
}

type HorarioRequestModel struct {
	Sigla string `json:"sigla"`
	Faixa string `json:"faixa"`
}

type HorarioResponseModel struct {
	HorarioId string `json:"horario_id"`
	Sigla     string `json:"sigla"`
	Faixa     string `json:"faixa"`
}

func (Horario) TableName() string {
	return "tb_horario"
}
