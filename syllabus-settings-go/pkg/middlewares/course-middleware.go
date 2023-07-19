package middlewares

import (
	"fmt"
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/gin-gonic/gin"
)

func CacheCourses(ctx *gin.Context) {
	courses := cache.GetCourses()

	if courses != nil {
		response := mappers.ToCourseResponseArray(&courses)
		fmt.Println(response)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.GetCourse("course" + courseId)

	if course != nil {
		response := mappers.ToCourseResponse(course)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CachePreRequisiteOrEquivalent(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.GetCourse("course" + courseId)

	if course != nil {
		courses := course.PreRequisiteCourses
		response := mappers.ToCourseResponseArray(courses)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheClassesByCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.GetCourse("course" + courseId)

	if course != nil {
		response := mappers.ToCourseClasses(course)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
