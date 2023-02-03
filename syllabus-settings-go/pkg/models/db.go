package models

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"gorm.io/gorm"
)

var db *gorm.DB

func init() {
	config.Connect()
	db = config.GetDB()
	db.AutoMigrate(&Book{}, &Dia{}, &Horario{}, &Turno{}, &Tipo{}, &Curso{}, &Disciplina{}, &HorarioAula{}, &Turma{}, &DisciplinaCurso{})
}
