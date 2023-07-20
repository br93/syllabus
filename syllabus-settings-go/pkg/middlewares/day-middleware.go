package middlewares

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/mappers"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CacheDays(ctx *gin.Context) {
	days := cache.Get("all-days")

	if days != "nil" {
		response := mappers.ToDayResponseArray(utils.UnmarshalDays(days))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}

func CacheDay(ctx *gin.Context) {
	dayId := ctx.Param("day_id")

	day := cache.Get("day" + dayId)

	if day != "nil" {
		response := mappers.ToDayResponse(utils.UnmarshalDay(day))
		ctx.JSON(http.StatusOK, response)
		ctx.Abort()
	}

	ctx.Next()
}
