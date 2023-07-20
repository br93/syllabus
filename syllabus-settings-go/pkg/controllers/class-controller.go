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

var NewClass models.Class

func CreateClass(ctx *gin.Context) {
	body := models.ClassRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	class := mappers.ToClass(&body)

	if err := services.CreateClass(class); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToClassResponse(class)

	cache.Flush()
	ctx.JSON(http.StatusCreated, response)
}

func GetClassByIdOrCode(ctx *gin.Context) {
	classId := ctx.Param("class_id")
	class, err := services.GetClassByIdOrCode(classId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Set("class", classId, class)
	response := mappers.ToClassResponse(class)

	ctx.JSON(http.StatusOK, response)

}

func GetClasses(ctx *gin.Context) {

	classes, err := services.GetClasses()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.SetAll("all-classes", classes)
	response := mappers.ToClassResponseArray(classes)

	ctx.JSON(http.StatusOK, response)
}

func UpdateClass(ctx *gin.Context) {
	classId := ctx.Param("class_id")
	body := models.ClassRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	class := mappers.ToClass(&body)
	update, err := services.UpdateClass(classId, class)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToClassResponse(update)

	cache.Flush()
	ctx.JSON(http.StatusOK, response)
}

func DeleteClass(ctx *gin.Context) {
	classId := ctx.Param("class_id")

	err := services.DeleteClass(classId)
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

func GetClassesByCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course, err := services.GetCourseByIdOrCode(courseId, "Classes")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Set("classes", courseId, course)
	response := mappers.ToCourseClasses(course)

	ctx.JSON(http.StatusOK, response)
}
