package models

type PreRequisiteCount struct {
	ID             int16  `gorm:json:"id"`
	CourseCode     string `json:"course_code"`
	AsPreRequisite int16  `gorm:"column:counts_as_pre_requisite" json:"as_pre_requisite"`
}

func (PreRequisiteCount) TableName() string {
	return "view_pre_requisites_count"
}
