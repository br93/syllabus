package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/unmarshal"
	"github.com/gin-gonic/gin"
)

func CacheDays(ctx *gin.Context) {
	days := cache.Get("all-days")

	if days != "nil" {
		response := unmarshal.UnmarshalDays(days)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheDay(ctx *gin.Context) {
	dayId := ctx.Param("day_id")

	day := cache.Get("day" + dayId)

	if day != "nil" {
		response := unmarshal.UnmarshalDay(day)
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
