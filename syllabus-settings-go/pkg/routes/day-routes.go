package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterDayRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/days", controllers.CreateDay)
	router.GET("/api/v1/config/days", controllers.GetDays)
	router.GET("/api/v1/config/days/:day_id", controllers.GetDayByIdOrNumber)
	router.PUT("/api/v1/config/days/:day_id", controllers.UpdateDay)
	router.DELETE("/api/v1/config/days/:day_id", controllers.DeleteDay)
}
