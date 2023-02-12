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

var NewDisciplina models.Disciplina

func CreateDisciplina(ctx *gin.Context) {
	body := models.DisciplinaRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	disciplina := mappers.ToDisciplina(&body)

	if err := services.CreateDisciplina(disciplina); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaResponse(disciplina)

	ctx.JSON(http.StatusCreated, response)
}

func GetDisciplinaByIdOrCodigo(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")

	disciplina, err := services.GetDisciplinaByIdOrCodigo(disciplinaId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaResponse(disciplina)

	ctx.JSON(http.StatusOK, response)

}

func GetDisciplinas(ctx *gin.Context) {

	disciplinas, err := services.GetDisciplinas()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaResponseArray(disciplinas)

	ctx.JSON(http.StatusOK, response)
}

func UpdateDisciplina(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")
	body := models.DisciplinaRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	disciplina := mappers.ToDisciplina(&body)
	update, err := services.UpdateDisciplina(disciplinaId, disciplina)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteDisciplina(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")

	err := services.DeleteDisciplina(disciplinaId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}

func AddPreRequisito(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")
	body := []models.DisciplinaCodigoRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	prerequisitos := mappers.FromDisciplinaCodigo(&body)
	update, err := services.CreatePreRequisito(disciplinaId, prerequisitos)

	if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaPreRequisito(update)

	ctx.JSON(http.StatusOK, response)
}

func AddEquivalente(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")
	body := []models.DisciplinaCodigoRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	equivalentes := mappers.FromDisciplinaCodigo(&body)
	update, err := services.CreateEquivalente(disciplinaId, equivalentes)

	if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaEquivalente(update)

	ctx.JSON(http.StatusOK, response)

}

func GetDisciplinaEquivalentes(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")

	disciplina, err := services.GetDisciplinaByIdOrCodigo(disciplinaId, "Equivalentes")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	equivalentes := disciplina.Equivalentes
	response := mappers.ToDisciplinaResponseArray(equivalentes)

	ctx.JSON(http.StatusOK, response)

}

func GetDisciplinaPreRequisitos(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")

	disciplina, err := services.GetDisciplinaByIdOrCodigo(disciplinaId, "PreRequisitos")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	preRequisitos := disciplina.PreRequisitos
	response := mappers.ToDisciplinaResponseArray(preRequisitos)

	ctx.JSON(http.StatusOK, response)

}

func GetTurmasByDisciplina(ctx *gin.Context) {
	disciplinaId := ctx.Param("disciplina_id")

	disciplina, err := services.GetDisciplinaByIdOrCodigo(disciplinaId, "Turmas", "Turmas.Turno")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaTurmas(disciplina)

	ctx.JSON(http.StatusOK, response)
}
