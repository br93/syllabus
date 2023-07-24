package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheClasses(ctx *gin.Context) {
	classes := cache.Get("all-classes")

	if classes != "nil" {
		response := unmarshal.UnmarshalClasses(classes)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheClass(ctx *gin.Context) {
	classId := ctx.Param("class_id")

	class := cache.Get("class" + classId)

	if class != "nil" {
		response := unmarshal.UnmarshalClass(class)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheClassesByCourse(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	course := cache.Get("classes" + courseId)

	if course != "nil" {
		response := unmarshal.UnmarshalCourseClasses(course)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
