package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCursoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/cursos", controllers.CreateCurso)
	router.GET("/api/v1/cursos", controllers.GetCursos)
	router.GET("/api/v1/cursos/:curso_id", controllers.GetCursoById)
	router.PUT("/api/v1/cursos/:curso_id", controllers.UpdateCurso)
	router.DELETE("/api/v1/cursos/:curso_id", controllers.DeleteCurso)
}
