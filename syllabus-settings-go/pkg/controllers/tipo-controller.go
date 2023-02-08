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

var NewTipo models.Tipo

func CreateTipo(ctx *gin.Context) {
	body := models.TipoRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	tipo := mappers.ToTipo(&body)

	if err := services.CreateTipo(tipo); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTipoResponse(tipo)

	ctx.JSON(http.StatusCreated, response)
}

func GetTipoById(ctx *gin.Context) {
	tipoId := ctx.Param("tipo_id")

	tipo, err := services.GetTipoById(tipoId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTipoResponse(tipo)

	ctx.JSON(http.StatusOK, response)

}

func GetTipos(ctx *gin.Context) {

	tipos, err := services.GetTipos()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTipoResponseArray(tipos)

	ctx.JSON(http.StatusOK, response)
}

func UpdateTipo(ctx *gin.Context) {
	tipoId := ctx.Param("tipo_id")
	body := models.TipoRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	tipo := mappers.ToTipo(&body)
	update, err := services.UpdateTipo(tipoId, tipo)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTipoResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteTipo(ctx *gin.Context) {
	tipoId := ctx.Param("tipo_id")

	err := services.DeleteTipo(tipoId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
