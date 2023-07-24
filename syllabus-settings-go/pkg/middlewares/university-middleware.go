package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheUniversities(ctx *gin.Context) {
	universities := cache.Get("all-universities")

	if universities != "nil" {
		response := unmarshal.UnmarshalUniversities(universities)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university := cache.Get("university" + universityId)

	if university != "nil" {
		response := unmarshal.UnmarshalUniversity(university)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
