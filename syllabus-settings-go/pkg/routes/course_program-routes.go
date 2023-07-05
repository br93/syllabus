package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterCourseProgramRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/courseprograms", controllers.CreateCourseProgram)
	router.GET("/api/v1/config/courseprograms", controllers.GetCoursePrograms)
	router.GET("/api/v1/config/courseprograms/:course_program_id", controllers.GetCourseProgramById)
	router.PUT("/api/v1/config/courseprograms/:course_program_id", controllers.UpdateCourseProgram)
	router.DELETE("/api/v1/config/courseprograms/:course_program_id", controllers.DeleteCourseProgram)
}
