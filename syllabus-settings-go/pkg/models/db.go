package models

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"gorm.io/gorm"
)

var DB *gorm.DB

func init() {
	config.Connect()
	DB = config.GetDB()
	DB.AutoMigrate(Book{}, Dia{}, Horario{}, TurnoEntity{}, Tipo{}, Curso{}, Disciplina{}, HorarioAula{}, Turma{}, DisciplinaCurso{})
}
