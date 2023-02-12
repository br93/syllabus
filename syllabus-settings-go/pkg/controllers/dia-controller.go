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

var NewDia models.Dia

func CreateDia(ctx *gin.Context) {
	body := models.DiaRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	dia := mappers.ToDia(&body)

	if err := services.CreateDia(dia); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDiaResponse(dia)

	ctx.JSON(http.StatusCreated, response)
}

func GetDiaByIdOrNumero(ctx *gin.Context) {
	diaId := ctx.Param("dia_id")

	dia, err := services.GetDiaByIdOrNumero(diaId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDiaResponse(dia)

	ctx.JSON(http.StatusOK, response)
}

func GetDias(ctx *gin.Context) {
	dias, err := services.GetDias()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDiaResponseArray(dias)

	ctx.JSON(http.StatusOK, response)
}

func UpdateDia(ctx *gin.Context) {
	diaId := ctx.Param("dia_id")
	body := models.DiaRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	dia := mappers.ToDia(&body)
	update, err := services.UpdateDia(diaId, dia)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDiaResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteDia(ctx *gin.Context) {
	diaId := ctx.Param("dia_id")

	err := services.DeleteDia(diaId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
