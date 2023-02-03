package routes

import (
	"github.com/br93/syllabus/syllabus-settings-go/pkg/controllers"
	"github.com/gorilla/mux"
)

var RegisterRoutes = func(router *mux.Router) {
	router.HandleFunc("/api/v1/books", controllers.CreateBook).Methods("POST")
	router.HandleFunc("/api/v1/books", controllers.GetBooks).Methods("GET")
	router.HandleFunc("/api/v1/books/{bookId}", controllers.GetBookById).Methods("GET")
	router.HandleFunc("/api/v1/books/{bookId}", controllers.UpdateBook).Methods("PUT")
	router.HandleFunc("/api/v1/books/{bookId}", controllers.DeleteBook).Methods("DELETE")
}
