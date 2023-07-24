package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/middlewares"
	"github.com/gin-gonic/gin"
)

var RegisterCourseRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/courses", controllers.CreateCourse)
	router.PUT("/api/v1/config/courses/:course_id", controllers.UpdateCourse)
	router.DELETE("/api/v1/config/courses/:course_id", controllers.DeleteCourse)
	router.PUT("/api/v1/config/courses/:course_id/prerequisites/", controllers.AddPreRequisite)
	router.PUT("/api/v1/config/courses/:course_id/equivalents/", controllers.AddEquivalent)

	router.GET("/api/v1/config/courses", middlewares.CacheCourses, controllers.GetCourses)
	router.GET("/api/v1/config/courses/:course_id", middlewares.CacheCourse, controllers.GetCourseByIdOrCode)
	router.GET("/api/v1/config/courses/:course_id/prerequisites/", middlewares.CachePreRequisites, controllers.GetCoursePreRequisites)
	router.GET("/api/v1/config/courses/:course_id/prerequisites/count", middlewares.CachePreRequisiteCountByCode, controllers.GetPreRequisiteCountByCode)
	router.GET("/api/v1/config/courses/:course_id/equivalents/", controllers.GetCourseEquivalents)

	router.GET("/api/v1/config/courses/:course_id/classes/", middlewares.CacheClassesByCourse, controllers.GetClassesByCourse)
}
