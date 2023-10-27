package routes

import (
	"github.com/br93/syllabus/syllabus-auth-go/initializers"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/controllers"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/middleware"
	"github.com/gin-gonic/gin"
)

var RegisterRoutes = func(router *gin.Engine) {
	router.GET("/health", initializers.CheckStatus)
	router.POST("/api/v1/auth/signup", controllers.Signup)
	router.POST("/api/v1/auth/login", controllers.Login)
	router.GET("/api/v1/auth/validate", middleware.Auth, controllers.Validate)
	router.GET("/api/v1/auth/user", middleware.Auth, controllers.GetMe)
}
