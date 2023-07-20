package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CachePrograms(ctx *gin.Context) {
	programs := cache.Get("all-programs")

	if programs != "nil" {
		response := mappers.ToProgramResponseArray(utils.UnmarshalPrograms(programs))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program := cache.Get("program" + programId)

	if program != "nil" {
		response := mappers.ToProgramResponse(utils.UnmarshalProgram(program))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheProgramsByUniversity(ctx *gin.Context) {
	universityId := ctx.Param("university_id")

	university := cache.Get("programs" + universityId)

	if university != "nil" {
		response := mappers.ToUniversityPrograms(utils.UnmarshalUniversity(university))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
