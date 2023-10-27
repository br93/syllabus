package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

var NewUniversity models.University

func CreateUniversity(ctx *gin.Context) {
	body := models.UniversityRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	university := mappers.ToUniversity(&body)

	if err := services.CreateUniversity(university); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToUniversityResponse(university)

	cache.Flush()
	ctx.JSON(http.StatusCreated, response)
}

func GetUniversityByIdOrCode(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university, err := services.GetUniversityByIdOrCode(universityId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToUniversityResponse(university)
	cache.Set("university", universityId, response)

	ctx.JSON(http.StatusOK, response)

}

func GetUniversities(ctx *gin.Context) {

	universities, err := services.GetUniversities()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToUniversityResponseArray(universities)
	cache.SetAll("all-universities", response)

	ctx.JSON(http.StatusOK, response)
}

func UpdateUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")
	body := models.UniversityRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	university := mappers.ToUniversity(&body)
	update, err := services.UpdateUniversity(universityId, university)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToUniversityResponse(update)

	cache.Flush()
	ctx.JSON(http.StatusOK, response)
}

func DeleteUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	err := services.DeleteUniversity(universityId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Flush()
	ctx.JSON(http.StatusNoContent, nil)
}
