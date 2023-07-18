package main

import (
	"log"
	"net/http"
	"time"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/routes"
	"github.com/gin-gonic/gin"
	"github.com/jellydator/ttlcache/v2"
)

var cache ttlcache.SimpleCache = ttlcache.NewCache()

func main() {

	cache.SetTTL(time.Duration(10 * time.Second))

	router := gin.Default()
	routes.RegisterRoutes(router)
	router.Run(":9010")

	log.Fatal(http.ListenAndServe("localhost:9010", router))

}
