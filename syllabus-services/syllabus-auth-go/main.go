package main

import (
	"log"
	"net/http"

	"github.com/br93/syllabus/syllabus-auth-go/initializers"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/routes"
	"github.com/gin-gonic/gin"
)

func init() {
	initializers.DBConnection()
	initializers.SyncDB()
}

func main() {
	r := gin.Default()
	routes.RegisterRoutes(r)
	r.Run(":8000")

	log.Fatal(http.ListenAndServe("localhost:8000", r))
}
