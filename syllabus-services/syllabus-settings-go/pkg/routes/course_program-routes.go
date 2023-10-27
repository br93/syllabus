package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/middlewares"
	"github.com/gin-gonic/gin"
)

var RegisterCourseProgramRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/course-programs", controllers.CreateCourseProgram)
	router.PUT("/api/v1/config/course-programs/:course_program_id", controllers.UpdateCourseProgram)
	router.DELETE("/api/v1/config/course-programs/:course_program_id", controllers.DeleteCourseProgram)

	router.GET("/api/v1/config/course-programs", middlewares.CacheCoursePrograms, controllers.GetCoursePrograms)
	router.GET("/api/v1/config/course-programs/:course_program_id", middlewares.CacheCourseProgram, controllers.GetCourseProgramById)

	router.GET("/api/v1/config/course-programs/courses/", middlewares.CacheCourseProgramsByCourseCodeIn, controllers.GetCourseProgramsByCourseCodeIn)
	router.GET("/api/v1/config/course-programs/programs/:program_code/type/:type_name", middlewares.CacheCourseProgramsByProgramAndCourseType, controllers.GetCourseProgramsByProgramAndCourseType)
	router.GET("/api/v1/config/course-programs/programs/:program_code/not-type/:type_name", middlewares.CacheCourseProgramsByProgramAndNotCourseType, controllers.GetCourseProgramsByProgramAndNotCourseType)

}
