package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/middlewares"
	"github.com/gin-gonic/gin"
)

var RegisterCourseTypeRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/course-types", controllers.CreateCourseType)
	router.PUT("/api/v1/config/course-types/:course_type_id", controllers.UpdateCourseType)
	router.DELETE("/api/v1/config/course-types/:course_type_id", controllers.DeleteCourseType)

	router.GET("/api/v1/config/course-types", middlewares.CacheCourseTypes, controllers.GetCourseTypes)
	router.GET("/api/v1/config/course-types/:course_type_id", middlewares.CacheCourseType, controllers.GetCourseTypeByIdOrName)
}
