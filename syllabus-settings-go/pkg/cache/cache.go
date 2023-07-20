package cache

import (
	"context"
	"encoding/json"
	"time"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
)

var cache = config.GetClient()

func SetAll(key string, value interface{}) {

	json, err := json.Marshal(value)
	if err != nil {
		panic(err)
	}

	cache.Set(context.Background(), key, json, 10*time.Minute)
}

func Set(key string, id string, value interface{}) {

	json, err := json.Marshal(value)
	if err != nil {
		panic(err)
	}

	cache.Set(context.Background(), key+id, json, 10*time.Minute)
}

func Get(key string) string {

	value, err := cache.Get(context.Background(), key).Result()
	if err != nil {
		return "nil"
	}

	return value
}

func Flush() {
	cache.FlushDB(context.Background())
}
