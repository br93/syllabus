package controllers

import (
	"encoding/json"
	"net/http"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CheckStatus(w http.ResponseWriter, r *http.Request) {
	health := utils.CheckStatus()
	response, _ := json.Marshal(health)

	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(http.StatusOK)
	w.Write(response)
}
