package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

var NewTurno models.Turno

func CreateTurno(ctx *gin.Context) {
	body := models.TurnoRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	turno := mappers.ToTurno(&body)

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

func GetTurnos(ctx *gin.Context) {

	turnos, err := services.GetTurnos()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurnoResponseArray(turnos)

	ctx.JSON(http.StatusOK, response)
}

func UpdateTurno(ctx *gin.Context) {
	turnoId := ctx.Param("turno_id")
	body := models.TurnoRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	turno := mappers.ToTurno(&body)
	update, err := services.UpdateTurno(turnoId, turno)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurnoResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteTurno(ctx *gin.Context) {
	turnoId := ctx.Param("turno_id")

	err := services.DeleteTurno(turnoId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
