package controllers

import (
	"net/http"
	"strings"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

var NewClassSchedule models.ClassSchedule

func CreateClassSchedule(ctx *gin.Context) {
	body := models.ClassScheduleRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	classSchedule := mappers.ToClassSchedule(&body)

	if err := services.CreateClassSchedule(classSchedule); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToClassScheduleResponse(classSchedule)

	cache.Flush()
	ctx.JSON(http.StatusCreated, response)
}

func GetClassScheduleById(ctx *gin.Context) {
	classScheduleId := ctx.Param("class_schedule_id")

	classSchedule, err := services.GetClassScheduleById(classScheduleId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Set("class-schedule", classScheduleId, classSchedule)
	response := mappers.ToClassScheduleResponse(classSchedule)

	ctx.JSON(http.StatusOK, response)

}

func GetClassSchedulesByClass(ctx *gin.Context) {
	classId := ctx.Param("class_id")

	classSchedules, err := services.GetClassScheduleByClassCode(classId, "Class", "Day", "Schedule")

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Set("class-schedule", classId, classSchedules)
	response := mappers.ToClassScheduleResponseArray(classSchedules)

	ctx.JSON(http.StatusOK, response)
}

func GetClassSchedules(ctx *gin.Context) {

	classSchedules, err := services.GetClassSchedules()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.SetAll("all-class-schedules", classSchedules)
	response := mappers.ToClassScheduleResponseArray(classSchedules)

	ctx.JSON(http.StatusOK, response)
}

func UpdateClassSchedule(ctx *gin.Context) {
	classScheduleId := ctx.Param("class_schedule_id")
	body := models.ClassScheduleRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	classSchedule := mappers.ToClassSchedule(&body)
	update, err := services.UpdateClassSchedule(classScheduleId, classSchedule)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToClassScheduleResponse(update)

	cache.Flush()
	ctx.JSON(http.StatusOK, response)
}

func DeleteClassSchedule(ctx *gin.Context) {
	classScheduleId := ctx.Param("class_schedule_id")

	err := services.DeleteClassSchedule(classScheduleId)
	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Flush()
	ctx.JSON(http.StatusNoContent, nil)
}
