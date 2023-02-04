package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

//CreatePost(*models.CreatePostRequest) (*models.DBPost, error)
//	UpdatePost(string, *models.UpdatePost) (*models.DBPost, error)
//	FindPostById(string) (*models.DBPost, error)
//	FindPosts(page int, limit int) ([]*models.DBPost, error)
//	DeletePost(string) error

func CreateTurno(req *models.TurnoEntity) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetTurnoById(turnoId string) (*models.TurnoEntity, error) {
	var turno models.TurnoEntity

	models.DB.First(&turno, "turno_id = ?", turnoId)

	if turno.ID == 0 {
		return &turno, errors.New("turno not found")
	}

	return &turno, nil
}
