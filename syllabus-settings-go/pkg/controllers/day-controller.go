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

var NewDay models.Day

func CreateDay(ctx *gin.Context) {
	body := models.DayRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	day := mappers.ToDay(&body)

	if err := services.CreateDay(day); err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDayResponse(day)

	cache.Flush()
	ctx.JSON(http.StatusCreated, response)
}

func GetDayByIdOrNumber(ctx *gin.Context) {
	dayId := ctx.Param("day_id")

	day, err := services.GetDayByIdOrNumber(dayId)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.Set("day", dayId, day)
	response := mappers.ToDayResponse(day)

	ctx.JSON(http.StatusOK, response)
}

func GetDays(ctx *gin.Context) {
	days, err := services.GetDays()

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	cache.SetAll("all-days", days)
	response := mappers.ToDayResponseArray(days)

	ctx.JSON(http.StatusOK, response)
}

func UpdateDay(ctx *gin.Context) {
	dayId := ctx.Param("day_id")
	body := models.DayRequestModel{}

	if err := ctx.ShouldBindJSON(&body); err != nil {
		utils.ErrorHandling(ctx, err)
		return
	}

	day := mappers.ToDay(&body)
	update, err := services.UpdateDay(dayId, day)

	if err != nil && strings.Contains(err.Error(), "not found") {
		ctx.AbortWithError(http.StatusNotFound, err)
		return
	} else if err != nil {
		ctx.AbortWithError(http.StatusBadRequest, err)
		return
	}

	response := mappers.ToDayResponse(update)

	cache.Flush()
	ctx.JSON(http.StatusOK, response)
}

func DeleteDay(ctx *gin.Context) {
	dayId := ctx.Param("day_id")

	err := services.DeleteDay(dayId)
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
