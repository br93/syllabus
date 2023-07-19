package cache

import (
	"context"
	"encoding/json"
	"time"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

var cache = config.GetClient()

func SetCourses(value []models.Course) {

	json, err := json.Marshal(value)
	if err != nil {
		panic(err)
	}

	cache.Set(context.Background(), "all-courses", json, 60*time.Second)
}

func SetCourse(key string, value *models.Course) {

	json, err := json.Marshal(value)
	if err != nil {
		panic(err)
	}

	cache.Set(context.Background(), "course"+key, json, 60*time.Second)
}

func GetCourses() []models.Course {

	value, err := cache.Get(context.Background(), "all-courses").Result()
	if err != nil {
		return nil
	}

	courses := []models.Course{}
	err = json.Unmarshal([]byte(value), &courses)
	if err != nil {
		panic(err)
	}

	return courses
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
