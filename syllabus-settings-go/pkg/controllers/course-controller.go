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

var NewCourse models.Course

func CreateCourse(ctx *gin.Context) {
	body := models.CourseRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	course := mappers.ToCourse(&body)

	if err := services.CreateCourse(course); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseResponse(course)

	ctx.JSON(http.StatusCreated, response)
}

func GetCourseByIdOrCode(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course, err := services.GetCourseByIdOrCode(courseId, "University")
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithStatusJSON(http.StatusNotFound, gin.H{"status": "404 not found", "message": err.Error()})
		return
	} else if err != nil {
		ctx.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"status": "500 bad request", "message": err.Error()})
		return
	}

	cache.SetCourse(courseId, course)
	response := mappers.ToCourseResponse(course)
	ctx.JSON(http.StatusOK, response)

}

func GetCourses(ctx *gin.Context) {

	courses, err := services.GetCourses("University")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseResponseArray(courses)

	ctx.JSON(http.StatusOK, response)
}

func UpdateCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")
	body := models.CourseRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	course := mappers.ToCourse(&body)
	update, err := services.UpdateCourse(courseId, course)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	err := services.DeleteCourse(courseId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}

func AddPreRequisite(ctx *gin.Context) {
	courseId := ctx.Param("course_id")
	body := []models.CourseCodeRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	prerequisites := mappers.FromCourseCode(&body)
	update, err := services.CreatePreRequisite(courseId, prerequisites)

	if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCoursePreRequisite(update)

	ctx.JSON(http.StatusOK, response)
}

func AddEquivalent(ctx *gin.Context) {
	courseId := ctx.Param("course_id")
	body := []models.CourseCodeRequestModel{}

	if err := ctx.BindJSON(&body); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	equivalents := mappers.FromCourseCode(&body)
	update, err := services.CreateEquivalent(courseId, equivalents)

	if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToCourseEquivalent(update)

	ctx.JSON(http.StatusOK, response)

}

func GetCourseEquivalents(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course, err := services.GetCourseByIdOrCode(courseId, "EquivalentCourses", "EquivalenteCourses.University")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.SetCourse(courseId, course)
	equivalents := course.EquivalentCourses
	response := mappers.ToCourseResponseArray(equivalents)

	ctx.JSON(http.StatusOK, response)

}

func GetCoursePreRequisites(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course, err := services.GetCourseByIdOrCode(courseId, "PreRequisiteCourses", "PreRequisiteCourses.University")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.SetCourse(courseId, course)
	preRequisites := course.PreRequisiteCourses
	response := mappers.ToCourseResponseArray(preRequisites)

	ctx.JSON(http.StatusOK, response)

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

	cache.SetCourse(courseId, course)
	response := mappers.ToCourseClasses(course)

	ctx.JSON(http.StatusOK, response)
}
