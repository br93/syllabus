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

	cache.Flush()
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

	response := mappers.ToCourseResponse(course)
	cache.Set("course", courseId, response)

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
	cache.SetAll("all-courses", response)

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

	cache.Flush()
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

	cache.Flush()
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

	cache.Flush()
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

	cache.Flush()
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

	equivalents := course.EquivalentCourses
	response := mappers.ToCourseResponseArray(equivalents)
	cache.Set("equivalents", courseId, response)

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

	response := mappers.ToCoursePreRequisite(course)
	cache.Set("pre-requisites", courseId, response)

	ctx.JSON(http.StatusOK, response)

}

func GetCoursesByProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program, err := services.GetProgramByIdOrCode(programId, "Courses", "Courses.Course", "Courses.CourseType")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramCourses(program)
	cache.Set("courses", programId, response)

	ctx.JSON(http.StatusOK, response)
}

func GetCoursesByUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university, err := services.GetUniversityByIdOrCode(universityId, "Courses", "Courses.University")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToUniversityCourses(university)
	cache.Set("courses", universityId, response)

	ctx.JSON(http.StatusOK, response)
}
