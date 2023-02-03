package models

import (
	"gorm.io/gorm"
)

type Tipo struct {
	gorm.Model
	TipoId    string `gorm:"" json:"tipo_id"`
	TipoNome  string `json:"tipo_nome"`
	TipoValor int16  `json:"tipo_valor"`
}
