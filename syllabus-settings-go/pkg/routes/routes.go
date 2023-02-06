package routes

import (
	"github.com/gin-gonic/gin"
)

var RegisterRoutes = func(router *gin.Engine) {
	RegisterHealthRoutes(router)
	RegisterTurnoRoutes(router)
	RegisterDiaRoutes(router)
	RegisterCursoRoutes(router)
	RegisterDisciplinaRoutes(router)
	RegisterHorarioRoutes(router)
	RegisterTurmaRoutes(router)
	RegisterHorarioAulaRoutes(router)
}
