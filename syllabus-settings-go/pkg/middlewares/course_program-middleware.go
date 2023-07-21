package middlewares

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheCoursePrograms(ctx *gin.Context) {
	coursePrograms := cache.Get("all-course-programs")

	if coursePrograms != "nil" {
		response := unmarshal.UnmarshalCourseProgramResponseArray(coursePrograms)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgram(ctx *gin.Context) {
	courseProgramId := ctx.Param("course_program_id")

	courseProgram := cache.Get("course-program" + courseProgramId)

	if courseProgram != "nil" {
		response := unmarshal.UnmarshalCourseProgramResponse(courseProgram)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgramsByCourseCodeIn(ctx *gin.Context) {
	codes, _ := ctx.GetQueryArray("code")

	coursePrograms := cache.Get("course-programs" + strings.Join(codes, "-"))

	if coursePrograms != "nil" {
		response := unmarshal.UnmarshalCourseProgramResponseArray(coursePrograms)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgramsByProgramAndCourseType(ctx *gin.Context) {
	programCode := ctx.Param("program_code")
	typeName := ctx.Param("type_name")
	id := programCode + typeName

	coursePrograms := cache.Get("course-programs" + id)

	if coursePrograms != "nil" {
		response := unmarshal.UnmarshalCourseProgramResponseArray(coursePrograms)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgramsByProgramAndNotCourseType(ctx *gin.Context) {
	programCode := ctx.Param("program_code")
	typeName := ctx.Param("type_name")
	id := programCode + "!" + typeName

	coursePrograms := cache.Get("course-programs" + id)

	if coursePrograms != "nil" {
		response := unmarshal.UnmarshalCourseProgramResponseArray(coursePrograms)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
