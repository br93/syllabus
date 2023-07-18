package main

import (
	"log"
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/routes"
	"github.com/gin-gonic/gin"
)

func main() {

	config.ConfigCache()

	router := gin.Default()
	routes.RegisterRoutes(router)
	router.Run(":9010")

	log.Fatal(http.ListenAndServe("localhost:9010", router))

}
