package utils

type Health struct {
	Status string `json:"status" `
}

func CheckStatus() *Health {
	var health Health
	health.Status = "UP"
	return &health
}
