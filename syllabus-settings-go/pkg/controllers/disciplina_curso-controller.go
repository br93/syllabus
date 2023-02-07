package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/gin-gonic/gin"
)

var NewDisciplinaCurso models.DisciplinaCurso

func CreateDisciplinaCurso(ctx *gin.Context) {
	body := models.DisciplinaCursoRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	disciplinacurso := mappers.ToDisciplinaCurso(&body)

	if err := services.CreateDisciplinaCurso(disciplinacurso); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaCursoResponse(disciplinacurso)

	ctx.JSON(http.StatusCreated, response)
}

func GetDisciplinaCursoById(ctx *gin.Context) {
	disciplinacursoId := ctx.Param("disciplinacurso_id")

	disciplinacurso, err := services.GetDisciplinaCursoById(disciplinacursoId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaCursoResponse(disciplinacurso)

	ctx.JSON(http.StatusOK, response)

}

func GetDisciplinaCursos(ctx *gin.Context) {

	disciplinacursos, err := services.GetDisciplinaCursos()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaCursoResponseArray(disciplinacursos)

	ctx.JSON(http.StatusOK, response)
}

func UpdateDisciplinaCurso(ctx *gin.Context) {
	disciplinacursoId := ctx.Param("disciplinacurso_id")
	body := models.DisciplinaCursoRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	disciplinacurso := mappers.ToDisciplinaCurso(&body)
	update, err := services.UpdateDisciplinaCurso(disciplinacursoId, disciplinacurso)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDisciplinaCursoResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteDisciplinaCurso(ctx *gin.Context) {
	disciplinacursoId := ctx.Param("disciplinacurso_id")

	err := services.DeleteDisciplinaCurso(disciplinacursoId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}
