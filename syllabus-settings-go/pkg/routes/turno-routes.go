package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterTurnoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/turnos", controllers.CreateTurno)
	router.GET("/api/v1/turnos", controllers.GetTurnos)
	router.GET("/api/v1/turnos/:turno_id", controllers.GetTurnoById)
	router.PUT("/api/v1/turnos/:turno_id", controllers.UpdateTurno)
	router.DELETE("/api/v1/turnos/:turno_id", controllers.DeleteTurno)
}
