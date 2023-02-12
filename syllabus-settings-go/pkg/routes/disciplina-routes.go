package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gin-gonic/gin"
)

var RegisterDisciplinaRoutes = func(router *gin.Engine) {
	router.POST("/api/v1/config/disciplinas", controllers.CreateDisciplina)
	router.GET("/api/v1/config/disciplinas", controllers.GetDisciplinas)
	router.GET("/api/v1/config/disciplinas/:disciplina_id", controllers.GetDisciplinaById)
	router.PUT("/api/v1/config/disciplinas/:disciplina_id", controllers.UpdateDisciplina)
	router.DELETE("/api/v1/config/disciplinas/:disciplina_id", controllers.DeleteDisciplina)
	router.PUT("/api/v1/config/disciplinas/:disciplina_id/pre/", controllers.AddPreRequisito)
	router.GET("/api/v1/config/disciplinas/:disciplina_id/pre/", controllers.GetDisciplinaPreRequisitos)
	router.PUT("/api/v1/config/disciplinas/:disciplina_id/equivalentes/", controllers.AddEquivalente)
	router.GET("/api/v1/config/disciplinas/:disciplina_id/equivalentes/", controllers.GetDisciplinaEquivalentes)
	router.GET("/api/v1/config/disciplinas/:disciplina_id/turmas/", controllers.GetTurmasByDisciplina)
}
