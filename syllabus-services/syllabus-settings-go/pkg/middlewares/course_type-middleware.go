package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheCourseTypes(ctx *gin.Context) {
	courseTypes := cache.Get("all-course-types")

	if courseTypes != "nil" {
		response := unmarshal.UnmarshalCourseTypes(courseTypes)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseType(ctx *gin.Context) {
	courseTypeId := ctx.Param("course_type_id")

	courseType := cache.Get("course-type" + courseTypeId)

	if courseType != "nil" {
		response := unmarshal.UnmarshalCourseType(courseType)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
