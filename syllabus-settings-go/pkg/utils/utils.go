package utils

import (
	"encoding/json"
	"io"
	"net/http"
)

type Health struct {
	Status string `json:"status" `
}

func ParseBody(r *http.Request, x interface{}) {
	if body, err := io.ReadAll(r.Body); err == nil {
		if err := json.Unmarshal([]byte(body), x); err != nil {
			return
		}
	}
}

func CheckStatus() *Health {
	var health Health
	health.Status = "UP"
	return &health
}
