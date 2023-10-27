package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/middlewares"
	"github.com/gin-gonic/gin"
)

var RegisterProgramRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/programs", controllers.CreateProgram)
	router.PUT("/api/v1/config/programs/:program_id", controllers.UpdateProgram)
	router.DELETE("/api/v1/config/programs/:program_id", controllers.DeleteProgram)

	router.GET("/api/v1/config/programs", middlewares.CachePrograms, controllers.GetPrograms)
	router.GET("/api/v1/config/programs/:program_id", middlewares.CacheProgram, controllers.GetProgramByIdOrCode)

	router.GET("/api/v1/config/programs/:program_id/courses/", middlewares.CacheCoursesByProgram, controllers.GetCoursesByProgram)
}
