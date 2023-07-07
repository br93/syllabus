package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterUniversityRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/universities", controllers.CreateUniversity)
	router.GET("/api/v1/config/universities", controllers.GetUniversities)
	router.GET("/api/v1/config/universities/:university_id", controllers.GetUniversityByIdOrCode)
	router.PUT("/api/v1/config/universities/:university_id", controllers.UpdateUniversity)
	router.DELETE("/api/v1/config/universities/:university_id", controllers.DeleteUniversity)

	router.GET("/api/v1/config/universities/:university_id/programs/", controllers.GetProgramsByUniversity)
	router.GET("/api/v1/config/universities/:university_id/courses/", controllers.GetCoursesByUniversity)
}
