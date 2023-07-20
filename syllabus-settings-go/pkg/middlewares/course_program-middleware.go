package middlewares

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CacheCoursePrograms(ctx *gin.Context) {
	coursePrograms := cache.Get("all-course-programs")

	if coursePrograms != "nil" {
		response := mappers.ToCourseProgramResponseArray(utils.UnmarshalCoursePrograms(coursePrograms))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgram(ctx *gin.Context) {
	courseProgramId := ctx.Param("course_program_id")

	courseProgram := cache.Get("course-program" + courseProgramId)

	if courseProgram != "nil" {
		response := mappers.ToCourseProgramResponse(utils.UnmarshalCourseProgram(courseProgram))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheCourseProgramsByCourseCodeIn(ctx *gin.Context) {
	codes, _ := ctx.GetQueryArray("code")

	coursePrograms := cache.Get("course-programs" + strings.Join(codes, "-"))

	if coursePrograms != "nil" {
		response := mappers.ToCourseProgramResponseArray(utils.UnmarshalCoursePrograms(coursePrograms))
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
		response := mappers.ToCourseProgramResponseArray(utils.UnmarshalCoursePrograms(coursePrograms))
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
		response := mappers.ToCourseProgramResponseArray(utils.UnmarshalCoursePrograms(coursePrograms))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
