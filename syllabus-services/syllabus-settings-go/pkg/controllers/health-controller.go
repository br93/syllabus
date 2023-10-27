package controllers

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func CheckStatus(ctx *gin.Context) {
	health := utils.CheckStatus()
	ctx.JSON(http.StatusOK, health)
}
