package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/gin-gonic/gin"
)

var NewHorarioAula models.HorarioAula

func CreateHorarioAula(ctx *gin.Context) {
	body := models.HorarioAulaRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	horarioaula := mappers.ToHorarioAula(&body)

	if err := services.CreateHorarioAula(horarioaula); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioAulaResponse(horarioaula)

	ctx.JSON(http.StatusCreated, response)
}

func GetHorarioAulaById(ctx *gin.Context) {
	horarioaulaId := ctx.Param("horarioaula_id")

	horarioaula, err := services.GetHorarioAulaById(horarioaulaId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioAulaResponse(horarioaula)

	ctx.JSON(http.StatusOK, response)

}

func GetHorarioAulas(ctx *gin.Context) {

	horarioaulas, err := services.GetHorarioAulas()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioAulaResponseArray(horarioaulas)

	ctx.JSON(http.StatusOK, response)
}

func UpdateHorarioAula(ctx *gin.Context) {
	horarioaulaId := ctx.Param("horarioaula_id")
	body := models.HorarioAulaRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	horarioaula := mappers.ToHorarioAula(&body)
	update, err := services.UpdateHorarioAula(horarioaulaId, horarioaula)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToHorarioAulaResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteHorarioAula(ctx *gin.Context) {
	horarioaulaId := ctx.Param("horarioaula_id")

	err := services.DeleteHorarioAula(horarioaulaId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
