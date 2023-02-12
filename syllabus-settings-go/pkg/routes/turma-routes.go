package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterTurmaRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/turmas", controllers.CreateTurma)
	router.GET("/api/v1/config/turmas", controllers.GetTurmas)
	router.GET("/api/v1/config/turmas/:turma_id", controllers.GetTurmaById)
	router.PUT("/api/v1/config/turmas/:turma_id", controllers.UpdateTurma)
	router.DELETE("/api/v1/config/turmas/:turma_id", controllers.DeleteTurma)
	router.GET("/api/v1/config/turmas/:turma_id/horarios/", controllers.GetHorariosAulaByTurma)
}
