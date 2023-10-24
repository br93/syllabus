package config

import (
	"github.com/redis/go-redis/v9"
)

func GetClient() *redis.Client {
	return redis.NewClient(&redis.Options{
		Addr:     "syllabus-redis:6379",
		Password: "",
		DB:       0,
	})
}
