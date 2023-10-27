package models

import (
	"time"

	"gorm.io/gorm"
)

type User struct {
	gorm.Model
	UserId   string `gorm:"index:idx_user_id,unique"`
	Email    string `gorm:"unique"`
	Password string
}

type UserRequestModel struct {
	Email    string `json:"email"`
	Password string `json:"password"`
}

type UserResponseModel struct {
	UserID    string    `json:"user_id"`
	Email     string    `json:"email"`
	CreatedAt time.Time `json:"created_at"`
	UpdatedAt time.Time `json:"updated_at"`
}

func (User) TableName() string {
	return "tb_usuario"
}
