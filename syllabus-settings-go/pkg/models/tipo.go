package models

import (
	"gorm.io/gorm"
)

type CourseType struct {
	gorm.Model
	TypeId    string `gorm:"index:idx_type_id,unique"`
	TypeName  string `gorm:"unique" json:"tipo_name"`
	TypeValue int16  `json:"type_value"`
}

type CourseTypeRequestModel struct {
	TypeName  string `json:"type_name" binding:"required"`
	TypeValue int16  `json:"type_value" binding:"required,gte=1,lte=10"`
}

type CourseTypeResponseModel struct {
	TypeId   string `json:"type_id"`
	TypeName string `json:"type_name"`
}

func (CourseType) TableName() string {
	return "tb_course_types"
}
