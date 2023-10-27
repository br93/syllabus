package initializers

import (
	"net/http"

	"github.com/gin-gonic/gin"
)

type Health struct {
	Status string `json:"status" `
}

func Status() *Health {
	var health Health
	health.Status = "UP"
	return &health
}

func CheckStatus(ctx *gin.Context) {
	health := Status()
	ctx.JSON(http.StatusOK, health)
}
