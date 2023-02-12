package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCursoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/cursos", controllers.CreateCurso)
	router.GET("/api/v1/config/cursos", controllers.GetCursos)
	router.GET("/api/v1/config/cursos/:curso_id", controllers.GetCursoByIdOrCodigo)
	router.PUT("/api/v1/config/cursos/:curso_id", controllers.UpdateCurso)
	router.DELETE("/api/v1/config/cursos/:curso_id", controllers.DeleteCurso)
	router.GET("/api/v1/config/cursos/:curso_id/disciplinas/", controllers.GetDisciplinasByCurso)
}
