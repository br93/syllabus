package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCourseTypeRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/course-types", controllers.CreateCourseType)
	router.GET("/api/v1/config/course-types", controllers.GetCourseTypes)
	router.GET("/api/v1/config/course-types/:course_type_id", controllers.GetCourseTypeByIdOrName)
	router.PUT("/api/v1/config/course-types/:course_type_id", controllers.UpdateCourseType)
	router.DELETE("/api/v1/config/course-types/:course_type_id", controllers.DeleteCourseType)
}
