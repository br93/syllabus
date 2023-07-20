package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CacheUniversities(ctx *gin.Context) {
	universities := cache.Get("all-universities")

	if universities != "nil" {
		response := mappers.ToUniversityResponseArray(utils.UnmarshalUniversities(universities))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university := cache.Get("university" + universityId)

	if university != "nil" {
		response := mappers.ToUniversityResponse(utils.UnmarshalUniversity(university))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
