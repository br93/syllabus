package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterHorarioRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/horarios", controllers.CreateHorario)
	router.GET("/api/v1/config/horarios", controllers.GetHorarios)
	router.GET("/api/v1/config/horarios/:horario_id", controllers.GetHorarioByIdOrSigla)
	router.PUT("/api/v1/config/horarios/:horario_id", controllers.UpdateHorario)
	router.DELETE("/api/v1/config/horarios/:horario_id", controllers.DeleteHorario)
}
