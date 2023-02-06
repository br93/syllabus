package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/gin-gonic/gin"
)

var NewTurma models.Turma

func CreateTurma(ctx *gin.Context) {
	body := models.TurmaRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	turma := mappers.ToTurma(&body)

	if err := services.CreateTurma(turma); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurmaResponse(turma)

	ctx.JSON(http.StatusCreated, response)
}

func GetTurmaById(ctx *gin.Context) {
	turmaId := ctx.Param("turma_id")

	turma, err := services.GetTurmaById(turmaId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurmaResponse(turma)

	ctx.JSON(http.StatusOK, response)

}

func GetTurmas(ctx *gin.Context) {

	turmas, err := services.GetTurmas()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurmaResponseArray(turmas)

	ctx.JSON(http.StatusOK, response)
}

func UpdateTurma(ctx *gin.Context) {
	turmaId := ctx.Param("turma_id")
	body := models.TurmaRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	turma := mappers.ToTurma(&body)
	update, err := services.UpdateTurma(turmaId, turma)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToTurmaResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteTurma(ctx *gin.Context) {
	turmaId := ctx.Param("turma_id")

	err := services.DeleteTurma(turmaId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
