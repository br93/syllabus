package unmarshal

import (
	"encoding/json"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
)

func UnmarshalPreRequisiteCount(value string) *models.PreRequisiteCount {
	var preRequisiteCount models.PreRequisiteCount

	err := json.Unmarshal([]byte(value), &preRequisiteCount)
	if err != nil {
		panic(err)
	}

	return &preRequisiteCount
}
