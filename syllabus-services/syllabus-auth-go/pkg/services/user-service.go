package services

import (
	"errors"
	"os"
	"time"

	"github.com/br93/syllabus/syllabus-auth-go/initializers"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/models"
	"github.com/golang-jwt/jwt/v4"
	"golang.org/x/crypto/bcrypt"
)

func CreateUser(req *models.User) error {
	user := initializers.DB.Create(req)

	if user.Error != nil {
		return user.Error
	}

	return nil
}

func GetUserById(id float64) (*models.User, error) {
	var user models.User

	initializers.DB.First(&user, "id", uint(id))

	if user.ID == 0 {
		return nil, errors.New("user not found")
	}

	return &user, nil
}

func GetUserByEmail(email string) (*models.User, error) {
	var user models.User

	initializers.DB.First(&user, "email", email)

	if user.ID == 0 {
		return nil, errors.New("user not found")
	}

	return &user, nil
}

func HashPassword(password string) ([]byte, error) {
	hash, err := bcrypt.GenerateFromPassword([]byte(password), 10)

	return hash, err
}

func StringHash(hash []byte) string {
	return string(hash)
}

func CompareHash(request string, response string) error {
	err := bcrypt.CompareHashAndPassword([]byte(request), []byte(response))

	return err
}

func GenerateToken(id uint) (string, error) {
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"sub": id,
		"exp": time.Now().Add(time.Hour).Unix(),
	})

	tokenString, err := token.SignedString([]byte(os.Getenv("SECRET")))

	return tokenString, err
}
