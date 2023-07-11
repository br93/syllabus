package services

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func GetPreRequisiteCountByCode(code string) *models.PreRequisiteCount {
	var preRequisiteCount models.PreRequisiteCount

	models.DB.First(&preRequisiteCount, "course_code", code)

	if preRequisiteCount.ID == 0 {
		return &models.PreRequisiteCount{CourseCode: code, AsPreRequisite: 0}
	}

	return &preRequisiteCount

}
