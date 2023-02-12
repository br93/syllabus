package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterHorarioAulaRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/horarioaulas", controllers.CreateHorarioAula)
	router.GET("/api/v1/config/horarioaulas", controllers.GetHorarioAulas)
	router.GET("/api/v1/config/horarioaulas/:horarioaula_id", controllers.GetHorarioAulaById)
	router.PUT("/api/v1/config/horarioaulas/:horarioaula_id", controllers.UpdateHorarioAula)
	router.DELETE("/api/v1/config/horarioaulas/:horarioaula_id", controllers.DeleteHorarioAula)
}
