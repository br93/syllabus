package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/middlewares"
	"github.com/gin-gonic/gin"
)

var RegisterCourseRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/courses", controllers.CreateCourse)
	router.GET("/api/v1/config/courses", middlewares.CacheCourses, controllers.GetCourses)
	router.GET("/api/v1/config/courses/:course_id", middlewares.CacheCourse, controllers.GetCourseByIdOrCode)
	router.PUT("/api/v1/config/courses/:course_id", controllers.UpdateCourse)
	router.DELETE("/api/v1/config/courses/:course_id", controllers.DeleteCourse)
	router.PUT("/api/v1/config/courses/:course_id/prerequisites/", controllers.AddPreRequisite)
	router.GET("/api/v1/config/courses/:course_id/prerequisites/", middlewares.CachePreRequisiteOrEquivalent, controllers.GetCoursePreRequisites)
	router.GET("/api/v1/config/courses/:course_id/prerequisites/count", controllers.GetPreRequisiteCountByCode)
	router.PUT("/api/v1/config/courses/:course_id/equivalents/", controllers.AddEquivalent)
	router.GET("/api/v1/config/courses/:course_id/equivalents/", middlewares.CachePreRequisiteOrEquivalent, controllers.GetCourseEquivalents)
	router.GET("/api/v1/config/courses/:course_id/classes/", controllers.GetClassesByCourse)
}
