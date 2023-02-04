package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/gin-gonic/gin"
)

var NewTurno models.TurnoEntity

func CreateTurno(ctx *gin.Context) {
	body := models.TurnoRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	turno := mappers.ToTurnoEntity(&body)

	if err := services.CreateTurno(turno); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurnoResponse(turno)

	ctx.JSON(http.StatusCreated, response)
}

func GetTurnoById(ctx *gin.Context) {
	turnoId := ctx.Param("turno_id")

	turno, err := services.GetTurnoById(turnoId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurnoResponse(turno)

	ctx.JSON(http.StatusOK, response)

}
