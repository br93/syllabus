package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheSchedules(ctx *gin.Context) {
	schedules := cache.Get("all-schedules")

	if schedules != "nil" {
		response := unmarshal.UnmarshalSchedules(schedules)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheSchedule(ctx *gin.Context) {
	scheduleId := ctx.Param("schedule_id")

	schedule := cache.Get("schedule" + scheduleId)

	if schedule != "nil" {
		response := unmarshal.UnmarshalSchedule(schedule)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
