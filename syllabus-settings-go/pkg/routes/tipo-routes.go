package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterTipoRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/tipos", controllers.CreateTipo)
	router.GET("/api/v1/config/tipos", controllers.GetTipos)
	router.GET("/api/v1/config/tipos/:tipo_id", controllers.GetTipoByIdOrNome)
	router.PUT("/api/v1/config/tipos/:tipo_id", controllers.UpdateTipo)
	router.DELETE("/api/v1/config/tipos/:tipo_id", controllers.DeleteTipo)
}
