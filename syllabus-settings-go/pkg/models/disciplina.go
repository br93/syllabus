package models

import (
	"errors"

	"gorm.io/gorm"
)

type Disciplina struct {
	gorm.Model
	DisciplinaId  string            `gorm:"" json:"disciplina_id"`
	Nome          string            `json:"nome"`
	Codigo        string            `json:"codigo"`
	CargaHoraria  int16             `json:"carga_horaria"`
	Turmas        []Turma           `json:"turmas"`
	Equivalentes  []*Disciplina     `gorm:"many2many:disciplinas_equivalentes"`
	PreRequisitos []*Disciplina     `gorm:"many2many:disciplinas_prerequisitos"`
	Cursos        []DisciplinaCurso `json:"cursos"`
}

func (d *Disciplina) CreateDisciplina() *Disciplina {
	DB.Create(&d)
	return d
}

func GetAllDisciplinas() []Disciplina {
	var Disciplinas []Disciplina
	DB.Find(&Disciplinas)
	return Disciplinas
}

func GetDisciplinaById(disciplinaId string) (*Disciplina, *gorm.DB, error) {
	var getDisciplina Disciplina
	db := DB.Where("disciplina_id=?", disciplinaId).Find(&getDisciplina)
	if getDisciplina.ID == 0 {
		return &getDisciplina, db, errors.New("Disciplina not found")
	}

	return &getDisciplina, db, nil
}

func DeleteDisciplina(disciplinaId string) error {
	var disciplina Disciplina
	DB.Where("disciplina_id=?", disciplinaId).Delete(&disciplina)

	if disciplina.ID == 0 {
		return errors.New("Disciplina not found")
	}

	return nil
}
