package models

import (
	"gorm.io/gorm"
)

type Tipo struct {
	gorm.Model
	TipoId    string `gorm:"index:idx_tipo_id,unique"`
	TipoNome  string `gorm:"unique" json:"tipo_nome"`
	TipoValor int16  `json:"tipo_valor"`
}

type TipoRequestModel struct {
	TipoNome  string `json:"tipo_nome"`
	TipoValor int16  `json:"tipo_valor"`
}

type TipoResponseModel struct {
	TipoId   string `json:"tipo_id"`
	TipoNome string `json:"tipo_nome"`
}

func (Tipo) TableName() string {
	return "tb_tipo"
}
