package models

import (
	"gorm.io/gorm"
)

type Course struct {
	gorm.Model
	CourseId            string          `gorm:"index:idx_course_id, unique"`
	CourseName          string          `json:"course_name"`
	CourseCode          string          `gorm:"unique" json:"course_code"`
	Workload            int16           `json:"workload"`
	Classes             []Class         `gorm:"foreignKey:CourseID;references:ID" json:"classes"`
	EquivalentCourses   *[]Course       `gorm:"many2many:tb_equivalent_courses"`
	PreRequisiteCourses *[]Course       `gorm:"many2many:tb_pre_requisite_courses"`
	CoursePrograms      []CourseProgram `gorm:"foreignKey:CourseID;references:ID" json:"course_programs"`
}

type CourseRequestModel struct {
	CourseName string `json:"course_name" binding:"required"`
	CourseCode string `json:"course_code" binding:"required,min=3,max=10"`
	Workload   int16  `json:"workload" binding:"required,gte=10,lte=200"`
}

type CourseResponseModel struct {
	CourseId   string `json:"course_id"`
	CourseCode string `json:"course_code"`
	CourseName string `json:"course_name"`
	Workload   int16  `json:"workload"`
}

type CourseCodeRequestModel struct {
	CourseCode string `json:"course_code"`
}

type EquivalentCoursesResponseModel struct {
	CourseId          string                `json:"course_id"`
	CourseCode        string                `json:"course_code"`
	CourseName        string                `json:"course_name"`
	Workload          int16                 `json:"workload"`
	EquivalentCourses []CourseResponseModel `json:"equivalent_courses"`
}

type PreRequisiteCoursesResponseModel struct {
	CourseId            string                `json:"course_id"`
	CourseCode          string                `json:"course_code"`
	CourseName          string                `json:"course_name"`
	Workload            int16                 `json:"workload"`
	PreRequisiteCourses []CourseResponseModel `json:"equivalent_courses"`
}

type CourseClassesResponseModel struct {
	CourseId   string               `json:"course_id"`
	CourseCode string               `json:"course_code"`
	CourseName string               `json:"course_name"`
	Classes    []ClassResponseModel `json:"classes"`
}

func (Course) TableName() string {
	return "tb_courses"
}
