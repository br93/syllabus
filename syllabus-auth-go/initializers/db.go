package initializers

import (
	"os"

	"github.com/br93/syllabus/syllabus-auth-go/pkg/models"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var DB *gorm.DB

func DBConnection() {

	dsn := os.Getenv("DB")
	connection, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})

	if err != nil {
		panic("Failed to connect to DB")
	}

	DB = connection
}

func SyncDB() {
	DB.AutoMigrate(&models.User{})
}

func GetDB() *gorm.DB {
	return DB
}
