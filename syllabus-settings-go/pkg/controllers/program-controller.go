package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

var NewProgram models.Program

func CreateProgram(ctx *gin.Context) {
	body := models.ProgramRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	program := mappers.ToProgram(&body)

	if err := services.CreateProgram(program); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramResponse(program)

	ctx.JSON(http.StatusCreated, response)
}

func GetProgramByIdOrCode(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program, err := services.GetProgramByIdOrCode(programId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramResponse(program)

	ctx.JSON(http.StatusOK, response)

}

func GetPrograms(ctx *gin.Context) {

	programs, err := services.GetPrograms()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramResponseArray(programs)

	ctx.JSON(http.StatusOK, response)
}

func UpdateProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")
	body := models.ProgramRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	program := mappers.ToProgram(&body)
	update, err := services.UpdateProgram(programId, program)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramResponse(update)

	ctx.JSON(http.StatusOK, response)
}

func DeleteProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	err := services.DeleteProgram(programId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	ctx.JSON(http.StatusNoContent, nil)
}

func GetCoursesByProgram(ctx *gin.Context) {
	programId := ctx.Param("program_id")

	program, err := services.GetProgramByIdOrCode(programId, "Courses", "Courses.Course", "Courses.CourseType")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToProgramCourses(program)

	ctx.JSON(http.StatusOK, response)
}
