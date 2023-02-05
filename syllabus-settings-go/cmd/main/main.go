package main

import (
	"log"
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/routes"
	"github.com/gin-gonic/gin"
)

func main() {

	router := gin.Default()
	routes.RegisterHealthRoutes(router)
	routes.RegisterTurnoRoutes(router)
	routes.RegisterDiaRoutes(router)
	routes.RegisterCursoRoutes(router)
	routes.RegisterDisciplinaRoutes(router)
	router.Run(":9010")

	log.Fatal(http.ListenAndServe("localhost:9010", router))

}
