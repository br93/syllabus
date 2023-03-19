package routes

import (
	"github.com/br93/syllabus/syllabus-auth-go/initializers"
	"github.com/gin-gonic/gin"
)

var RegisterRoutes = func(router *gin.Engine) {
	router.GET("/health", initializers.CheckStatus)
}
