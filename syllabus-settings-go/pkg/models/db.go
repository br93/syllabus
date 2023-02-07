package models

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"gorm.io/gorm"
)

var DB *gorm.DB

func init() {
	config.Connect()
	DB = config.GetDB()
	DB.AutoMigrate(Dia{}, Horario{}, Turno{}, Tipo{}, Curso{}, Disciplina{}, HorarioAula{}, Turma{}, DisciplinaCurso{})
}

func DBConfig(db *gorm.DB, preload []string) *gorm.DB {
	if preload == nil {
		return db
	}

	for i := 0; i < len(preload); i++ {
		db = eagerLoading(db, preload[i])
	}

	return db

}

func eagerLoading(db *gorm.DB, preload string) *gorm.DB {
	return db.Preload(preload)
}
