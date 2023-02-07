package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterDisciplinaCursoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/disciplinacursos", controllers.CreateDisciplinaCurso)
	router.GET("/api/v1/disciplinacursos", controllers.GetDisciplinaCursos)
	router.GET("/api/v1/disciplinacursos/:disciplinacurso_id", controllers.GetDisciplinaCursoById)
	router.PUT("/api/v1/disciplinacursos/:disciplinacurso_id", controllers.UpdateDisciplinaCurso)
	router.DELETE("/api/v1/disciplinacursos/:disciplinacurso_id", controllers.DeleteDisciplinaCurso)
}
