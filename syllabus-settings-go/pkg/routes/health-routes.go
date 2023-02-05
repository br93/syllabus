package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterHealthRoutes = func(router *gin.Engine) {
	router.GET("/health", controllers.CheckStatus)
}
