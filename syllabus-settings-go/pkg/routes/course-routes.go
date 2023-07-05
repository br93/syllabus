package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCourseRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/courses", controllers.CreateCourse)
	router.GET("/api/v1/config/courses", controllers.GetCourses)
	router.GET("/api/v1/config/courses/:course_id", controllers.GetCourseByIdOrCode)
	router.PUT("/api/v1/config/courses/:course_id", controllers.UpdateCourse)
	router.DELETE("/api/v1/config/courses/:course_id", controllers.DeleteCourse)
	router.PUT("/api/v1/config/courses/:course_id/prerequisites/", controllers.AddPreRequisite)
	router.GET("/api/v1/config/courses/:course_id/prerequisites/", controllers.GetCoursePreRequisites)
	router.PUT("/api/v1/config/courses/:course_id/equivalents/", controllers.AddEquivalent)
	router.GET("/api/v1/config/courses/:course_id/equivalents/", controllers.GetCourseEquivalents)
	router.GET("/api/v1/config/courses/:course_id/classes/", controllers.GetClassesByCourse)
}
