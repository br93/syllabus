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

var NewHorario models.Horario

func CreateHorario(ctx *gin.Context) {
	body := models.HorarioRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	horario := mappers.ToHorario(&body)

	if err := services.CreateHorario(horario); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioResponse(horario)

	ctx.JSON(http.StatusCreated, response)
}

func GetHorarioById(ctx *gin.Context) {
	horarioId := ctx.Param("horario_id")

	horario, err := services.GetHorarioById(horarioId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioResponse(horario)

	ctx.JSON(http.StatusOK, response)

}

func GetHorarios(ctx *gin.Context) {

	horarios, err := services.GetHorarios()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioResponseArray(horarios)

	ctx.JSON(http.StatusOK, response)
}

func UpdateHorario(ctx *gin.Context) {
	horarioId := ctx.Param("horario_id")
	body := models.HorarioRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	horario := mappers.ToHorario(&body)
	update, err := services.UpdateHorario(horarioId, horario)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteHorario(ctx *gin.Context) {
	horarioId := ctx.Param("horario_id")

	err := services.DeleteHorario(horarioId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
