package utils

import (
	"github.com/br93/syllabus/syllabus-auth-go/pkg/models"
	"github.com/google/uuid"
)

func ToUser(req *models.UserRequestModel) *models.User {

	new := models.User{UserId: uuid.NewString(), Email: req.Email, Password: req.Password}

	return &new
}

func ToUserResponse(user *models.User) *models.UserResponseModel {

	new := models.UserResponseModel{UserID: user.UserId, Email: user.Email, CreatedAt: user.CreatedAt, UpdatedAt: user.UpdatedAt}

	return &new
}
