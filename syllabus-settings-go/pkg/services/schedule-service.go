package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateSchedule(req *models.Schedule) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetScheduleById(scheduleId string) (*models.Schedule, error) {
	var schedule models.Schedule

	models.DB.First(&schedule, "schedule_id", scheduleId)

	if schedule.ID == 0 {
		return &schedule, errors.New("schedule not found")
	}

	return &schedule, nil
}

func GetScheduleByCode(sigla string) (*models.Schedule, error) {
	var schedule models.Schedule

	models.DB.First(&schedule, "schedule_code", sigla)

	if schedule.ID == 0 {
		return &schedule, errors.New("schedule not found")
	}

	return &schedule, nil
}

func GetScheduleByIdOrCode(schedule string) (*models.Schedule, error) {
	if utils.IsValidUUID(schedule) {
		return GetScheduleById(schedule)
	}

	return GetScheduleByCode(schedule)
}

func GetSchedules() (*[]models.Schedule, error) {

	var schedules []models.Schedule

	result := models.DB.Find(&schedules)

	if result.Error != nil {
		return &schedules, result.Error
	}

	return &schedules, nil
}

func UpdateSchedule(schedule string, req *models.Schedule) (*models.Schedule, error) {
	response, err := GetScheduleByIdOrCode(schedule)

	if err != nil {
		return req, err
	}

	response.ScheduleCode = req.ScheduleCode
	response.TimeRange = req.TimeRange
	response.TimeOfDay = req.TimeOfDay

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func DeleteSchedule(schedule string) error {
	get, err := GetScheduleByIdOrCode(schedule)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
