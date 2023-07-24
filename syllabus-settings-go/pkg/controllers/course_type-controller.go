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

var NewCourseType models.CourseType

func CreateCourseType(ctx *gin.Context) {
	body := models.CourseTypeRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	courseType := mappers.ToCourseType(&body)

	if err := services.CreateCourseType(courseType); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseTypeResponse(courseType)

	cache.Flush()
	ctx.JSON(http.StatusCreated, response)
}

func GetCourseTypeByIdOrName(ctx *gin.Context) {
	courseTypeId := ctx.Param("course_type_id")
	courseType, err := services.GetCourseTypeByIdOrName(courseTypeId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseTypeResponse(courseType)
	cache.Set("course-type", courseTypeId, response)

	ctx.JSON(http.StatusOK, response)

}

func GetCourseTypes(ctx *gin.Context) {

	courseTypes, err := services.GetCourseTypes()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseTypeResponseArray(courseTypes)
	cache.SetAll("all-course-types", response)

	ctx.JSON(http.StatusOK, response)
}

func UpdateCourseType(ctx *gin.Context) {
	courseTypeId := ctx.Param("course_type_id")
	body := models.CourseTypeRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	courseType := mappers.ToCourseType(&body)
	update, err := services.UpdateCourseType(courseTypeId, courseType)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseTypeResponse(update)

	cache.Flush()
	ctx.JSON(http.StatusOK, response)
}

func DeleteCourseType(ctx *gin.Context) {
	courseTypeId := ctx.Param("course_type_id")

	err := services.DeleteCourseType(courseTypeId)
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
