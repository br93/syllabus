package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterClassRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/classes", controllers.CreateClass)
	router.GET("/api/v1/config/classes", controllers.GetClasses)
	router.GET("/api/v1/config/classes/:class_id", controllers.GetClassByIdOrCode)
	router.PUT("/api/v1/config/classes/:class_id", controllers.UpdateClass)
	router.DELETE("/api/v1/config/classes/:class_id", controllers.DeleteClass)

	router.GET("/api/v1/config/classes/:class_id/schedules", controllers.GetClassSchedulesByClass)

}
