package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheCourses(ctx *gin.Context) {
	courses := cache.Get("all-courses")

	if courses != "nil" {
		response := unmarshal.UnmarshalCourses(courses)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("course" + courseId)

	if course != "nil" {
		response := unmarshal.UnmarshalCourse(course)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CachePreRequisites(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("pre-requisites" + courseId)

	if course != "nil" {
		response := unmarshal.UnmarshalPreRequisiteCourses(course)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCoursesByProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program := cache.Get("courses" + programId)

	if program != "nil" {
		response := unmarshal.UnmarshalProgramCourses(program)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCoursesByUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university := cache.Get("courses" + universityId)

	if university != "nil" {
		response := unmarshal.UnmarshalUniversityCourses(university)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()

}

func CachePreRequisiteCountByCode(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	preRequisiteCount := cache.Get("pre-requisite-count" + courseId)

	if preRequisiteCount != "nil" {
		ctx.JSON(http.StatusOK, unmarshal.UnmarshalPreRequisiteCount(preRequisiteCount))
		ctx.Abort()
	}

	ctx.Next()
}
