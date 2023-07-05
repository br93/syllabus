package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCourseRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/courses", controllers.CreateCourse)
	router.GET("/api/v1/config/courses", controllers.GetCourses)
	router.GET("/api/v1/config/courses/:course_id", controllers.GetCourseByIdOrCodigo)
	router.PUT("/api/v1/config/courses/:course_id", controllers.UpdateCourse)
	router.DELETE("/api/v1/config/courses/:course_id", controllers.DeleteCourse)
	router.PUT("/api/v1/config/courses/:course_id/pre/", controllers.AddPreRequisite)
	router.GET("/api/v1/config/courses/:course_id/pre/", controllers.GetCoursePreRequisites)
	router.PUT("/api/v1/config/courses/:course_id/equivalentes/", controllers.AddEquivalent)
	router.GET("/api/v1/config/courses/:course_id/equivalentes/", controllers.GetCourseEquivalents)
	router.GET("/api/v1/config/courses/:course_id/turmas/", controllers.GetClassesByCourse)
}
