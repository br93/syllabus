package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterDiaRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/dias", controllers.CreateDia)
	router.GET("/api/v1/config/dias", controllers.GetDias)
	router.GET("/api/v1/config/dias/:dia_id", controllers.GetDiaById)
	router.PUT("/api/v1/config/dias/:dia_id", controllers.UpdateDia)
	router.DELETE("/api/v1/config/dias/:dia_id", controllers.DeleteDia)
}
