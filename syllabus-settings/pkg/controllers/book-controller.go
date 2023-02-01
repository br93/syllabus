package controllers

import (
	"encoding/json"
	"fmt"
	"net/http"

	"github.com/br93/syllabus/syllabus-settings/pkg/models"
	"github.com/br93/syllabus/syllabus-settings/pkg/utils"
	"github.com/google/uuid"
	"github.com/gorilla/mux"
)

var NewBook models.Book

func GetBooks(w http.ResponseWriter, r *http.Request) {
	books := models.GetAllBooks()
	res, _ := json.Marshal(books)
	w.Header().Set("Content-Type", "pkglication/json")
	w.WriteHeader(http.StatusOK)
	w.Write(res)
}

func GetBookById(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	bookId, ok := vars["bookId"]
	if !ok {
		fmt.Println("id is missing in parameters")
	}
	bookDetails, _, error := models.GetBookById(bookId)

	if error != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(error.Error()))
		return
	}

	res, _ := json.Marshal(bookDetails)
	w.Header().Set("Content-Type", "pkglication/json")
	w.WriteHeader(http.StatusOK)
	w.Write(res)
}

func CreateBook(w http.ResponseWriter, r *http.Request) {
	book := &models.Book{}
	utils.ParseBody(r, book)
	book.BookId = uuid.New().String()
	newBook := book.CreateBook()

	res, _ := json.Marshal(newBook)
	w.Header().Set("Content-Type", "pkglication/json")
	w.WriteHeader(http.StatusCreated)
	w.Write(res)
}

func DeleteBook(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	bookId, ok := vars["bookId"]
	if !ok {
		fmt.Println("id is missing in parameters")
	}

	error := models.DeleteBook(bookId)

	if error != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(error.Error()))
		return
	}
	w.WriteHeader(http.StatusNoContent)

}

func UpdateBook(w http.ResponseWriter, r *http.Request) {
	var updateBook = &models.Book{}
	utils.ParseBody(r, updateBook)
	vars := mux.Vars(r)
	bookId, ok := vars["bookId"]
	if !ok {
		fmt.Println("id is missing in parameters")
	}

	bookDetails, db, error := models.GetBookById(bookId)

	if error != nil {
		w.WriteHeader(http.StatusNotFound)
		w.Write([]byte(error.Error()))
		return
	}

	bookDetails.Name = updateValues(bookDetails.Name, updateBook.Name)
	bookDetails.Author = updateValues(bookDetails.Author, updateBook.Author)
	bookDetails.Publication = updateValues(bookDetails.Publication, updateBook.Publication)

	db.Save(&bookDetails)

	res, _ := json.Marshal(bookDetails)
	w.Header().Set("Content-Type", "pkglication/json")
	w.WriteHeader(http.StatusOK)
	w.Write(res)
}

func updateValues(oldValue string, newValue string) string {
	if newValue != "" {
		return newValue
	}

	return oldValue
}

func CheckStatusBooks(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	w.Write([]byte("Hello World"))
}
