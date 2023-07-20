package controllers

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/cache"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/services"
	"github.com/gin-gonic/gin"
)

func GetPreRequisiteCountByCode(ctx *gin.Context) {
	courseId := ctx.Param("course_id")

	preRequisiteCount := services.GetPreRequisiteCountByCode(courseId)

	cache.Set("pre-requisite-count", courseId, preRequisiteCount)

	ctx.JSON(http.StatusOK, preRequisiteCount)

}
