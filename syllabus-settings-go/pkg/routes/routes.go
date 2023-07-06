package routes

import (
	"github.com/gin-gonic/gin"
)

var RegisterRoutes = func(router *gin.Engine) {
	RegisterHealthRoutes(router)
	RegisterDayRoutes(router)
	RegisterProgramRoutes(router)
	RegisterCourseRoutes(router)
	RegisterScheduleRoutes(router)
	RegisterClassRoutes(router)
	RegisterClassScheduleRoutes(router)
	RegisterCourseTypeRoutes(router)
	RegisterCourseProgramRoutes(router)
	RegisterUniversityRoutes(router)
}
