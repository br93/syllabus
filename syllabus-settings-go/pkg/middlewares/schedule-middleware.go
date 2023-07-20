package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CacheSchedules(ctx *gin.Context) {
	schedules := cache.Get("all-schedules")

	if schedules != "nil" {
		response := mappers.ToScheduleResponseArray(utils.UnmarshalSchedules(schedules))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheSchedule(ctx *gin.Context) {
	scheduleId := ctx.Param("schedule_id")

	schedule := cache.Get("schedule" + scheduleId)

	if schedule != "nil" {
		response := mappers.ToScheduleResponse(utils.UnmarshalSchedule(schedule))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
