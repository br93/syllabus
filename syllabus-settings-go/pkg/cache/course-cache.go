package cache

import (
	"context"
	"encoding/json"
	"time"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

var cache = config.GetClient()

func SetCourse(key string, value *models.Course) {

	json, err := json.Marshal(value)
	if err != nil {
		panic(err)
	}

	cache.Set(context.Background(), "course"+key, json, 10*time.Second)
}

func GetCourse(key string) *models.Course {

	value, err := cache.Get(context.Background(), key).Result()

	if err != nil {
		return nil
	}

	course := models.Course{}
	err = json.Unmarshal([]byte(value), &course)
	if err != nil {
		panic(err)
	}

	course.CourseName = course.CourseName + " CACHED"
	return &course

}
