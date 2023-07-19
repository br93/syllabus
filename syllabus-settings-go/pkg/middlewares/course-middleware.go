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

func CacheClassesByCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("course" + courseId)

	if course != "nil" {
		response := mappers.ToCourseClasses(utils.UnmarshalCourse(course))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
