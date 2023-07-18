package config

import (
	"time"

	"github.com/jellydator/ttlcache/v2"
)

var cache ttlcache.SimpleCache = ttlcache.NewCache()

func ConfigCache() {
	cache.SetTTL(time.Duration(10 * time.Second))
}

func GetCache() ttlcache.SimpleCache {
	return cache
}
