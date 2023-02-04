package models

import (
	"errors"

	"gorm.io/gorm"
)

type Book struct {
	gorm.Model
	Name        string `gorm:"" json:"name"`
	Author      string `json:"author"`
	Publication string `json:"publication"`
	BookId      string `json:"bookId"`
}

func (b *Book) CreateBook() *Book {
	DB.Create(&b)
	return b
}

func GetAllBooks() []Book {
	var Books []Book
	DB.Find(&Books)
	return Books
}

func GetBookById(bookId string) (*Book, *gorm.DB, error) {
	var getBook Book
	db := DB.Where("book_id=?", bookId).Find(&getBook)
	if getBook.ID == 0 {
		return &getBook, db, errors.New("Book not found")
	}

	return &getBook, db, nil
}

func DeleteBook(bookId string) error {
	var book Book
	DB.Where("book_id=?", bookId).Delete(&book)

	if book.ID == 0 {
		return errors.New("Book not found")
	}

	return nil
}
