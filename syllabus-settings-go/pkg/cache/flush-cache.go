package cache

import (
	"context"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/config"
)

func Flush() {
	config.GetClient().FlushAll(context.Background())
}
