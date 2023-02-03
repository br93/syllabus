package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gorilla/mux"
)

var RegisterHealthRoutes = func(router *mux.Router) {
	router.HandleFunc("/health", controllers.CheckStatus).Methods("GET")
}
