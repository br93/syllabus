package cache

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

type ClassCache interface {
	SetClass(key string, value *models.Class)
	GetClass(key string) *models.Class
}
