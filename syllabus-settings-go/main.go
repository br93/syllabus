package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/routes"
	"github.com/gin-gonic/gin"
)

func init() {
	config.LoadEnv()
}

func main() {
	fmt.Println("CREDENTIALS: " + os.Getenv("DB_CREDENTIALS"))

	router := gin.Default()
	routes.RegisterRoutes(router)
	router.Run(":9010")

	log.Fatal(http.ListenAndServe("localhost:9010", router))

}
