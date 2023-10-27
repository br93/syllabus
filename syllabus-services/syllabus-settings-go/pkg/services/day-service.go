package services

import (
	"errors"
	"strconv"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateDay(req *models.Day) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetDayById(dayId string) (*models.Day, error) {
	var day models.Day

	models.DB.First(&day, "day_id", dayId)

	if day.ID == 0 {
		return &day, errors.New("day not found")
	}

	return &day, nil
}

func GetDayByNumber(number int16) (*models.Day, error) {
	var day models.Day

	models.DB.First(&day, "day_number", number)

	if day.ID == 0 {
		return &day, errors.New("day not found")
	}

	return &day, nil
}

func GetDayByIdOrNumber(day string) (*models.Day, error) {
	if utils.IsValidUUID(day) {
		return GetDayById(day)
	}

	dayNumber, err := strconv.ParseInt(day, 10, 16)

	if err != nil {
		return &models.Day{}, errors.New("day not found")
	}

	return GetDayByNumber(int16(dayNumber))
}

func GetDays() (*[]models.Day, error) {
	var days []models.Day

	result := models.DB.Find(&days)

	if result.Error != nil {
		return &days, result.Error
	}

	return &days, nil
}

func UpdateDay(day string, req *models.Day) (*models.Day, error) {
	response, err := GetDayByIdOrNumber(day)

	if err != nil {
		return req, err
	}

	response.DayName = req.DayName
	response.DayNumber = req.DayNumber

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil
}

func DeleteDay(day string) error {
	get, err := GetDayByIdOrNumber(day)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
