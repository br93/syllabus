package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterDisciplinaCursoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/disciplinacursos", controllers.CreateDisciplinaCurso)
	router.GET("/api/v1/config/disciplinacursos", controllers.GetDisciplinaCursos)
	router.GET("/api/v1/config/disciplinacursos/:disciplinacurso_id", controllers.GetDisciplinaCursoById)
	router.PUT("/api/v1/config/disciplinacursos/:disciplinacurso_id", controllers.UpdateDisciplinaCurso)
	router.DELETE("/api/v1/config/disciplinacursos/:disciplinacurso_id", controllers.DeleteDisciplinaCurso)
}
