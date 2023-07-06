package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterScheduleRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/schedules", controllers.CreateSchedule)
	router.GET("/api/v1/config/schedules", controllers.GetSchedules)
	router.GET("/api/v1/config/schedules/:schedule_id", controllers.GetScheduleByIdOrCode)
	router.PUT("/api/v1/config/schedules/:schedule_id", controllers.UpdateSchedule)
	router.DELETE("/api/v1/config/schedules/:schedule_id", controllers.DeleteSchedule)
}
