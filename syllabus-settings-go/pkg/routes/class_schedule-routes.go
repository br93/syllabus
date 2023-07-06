package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterClassScheduleRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/class-schedules", controllers.CreateClassSchedule)
	router.GET("/api/v1/config/class-schedules", controllers.GetClassSchedules)
	router.GET("/api/v1/config/class-schedules/:class_schedule_id", controllers.GetClassScheduleById)
	router.PUT("/api/v1/config/class-schedules/:class_schedule_id", controllers.UpdateClassSchedule)
	router.DELETE("/api/v1/config/class-schedules/:class_schedule_id", controllers.DeleteClassSchedule)

}
