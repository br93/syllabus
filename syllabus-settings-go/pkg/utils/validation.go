package utils

import (
	"errors"
	"net/http"

	"github.com/gin-gonic/gin"
	"github.com/go-playground/validator/v10"
	"github.com/google/uuid"
)

type ErrorMsg struct {
	Field   string `json:"field"`
	Message string `json:"message"`
}

func getMessage(fe validator.FieldError) string {

	switch fe.Tag() {
	case "required":
		return "This field is required"
	case "lte":
		return "Should be less than " + fe.Param()
	case "gte":
		return "Should be greater than " + fe.Param()
	case "min":
		return "Should have more than " + fe.Param() + " characters"
	case "max":
		return "Should have less than " + fe.Param() + " characters"
	}

	return fe.Error()
}

func ErrorHandling(ctx *gin.Context, err error) {
	var ve validator.ValidationErrors

	if errors.As(err, &ve) {
		out := make([]ErrorMsg, len(ve))
		for i, fe := range ve {
			out[i] = ErrorMsg{fe.Field(), getMessage(fe)}
		}
		ctx.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"errors": out})
	}

}

func IsValidUUID(u string) bool {
	_, err := uuid.Parse(u)
	return err == nil
}
