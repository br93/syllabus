package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CacheCourses(ctx *gin.Context) {
	courses := cache.Get("all-courses")

	if courses != "nil" {
		response := mappers.ToCourseResponseArray(utils.UnmarshalCourses(courses))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("course" + courseId)

	if course != "nil" {
		response := mappers.ToCourseResponse(utils.UnmarshalCourse(course))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CachePreRequisite(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("course" + courseId)

	if course != "nil" {
		courses := utils.UnmarshalCourse(course).PreRequisiteCourses
		response := mappers.ToCourseResponseArray(courses)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCoursesByProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program := cache.Get("courses" + programId)

	if program != "nil" {
		response := mappers.ToProgramCourses(utils.UnmarshalProgram(program))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCoursesByUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university := cache.Get("courses" + universityId)

	if university != "nil" {
		response := mappers.ToUniversityCourses(utils.UnmarshalUniversity(university))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()

}

func CachePreRequisiteCountByCode(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	preRequisiteCount := cache.Get("pre-requisite-count" + courseId)

	if preRequisiteCount != "nil" {
		ctx.JSON(http.StatusOK, utils.UnmarshalPreRequisiteCount(preRequisiteCount))
		ctx.Abort()
	}

	ctx.Next()
}
