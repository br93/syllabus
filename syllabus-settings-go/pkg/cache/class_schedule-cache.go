package cache

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

type ClassScheduleCache interface {
	SetClassSchedule(key string, value *models.ClassSchedule)
	GetClassSchedule(key string) *models.ClassSchedule
}
