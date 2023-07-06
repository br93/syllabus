package services

import (
	"errors"

	"github.com/br93/syllabus/syllabus-settings-go/pkg/models"
	"github.com/br93/syllabus/syllabus-settings-go/pkg/utils"
)

func CreateCourse(req *models.Course) error {
	create := models.DB.Create(req)

	if create.Error != nil {
		return create.Error
	}

	return nil
}

func GetCourseById(courseId string, preload ...string) (*models.Course, error) {
	var course models.Course

	models.DBConfig(models.DB, preload).First(&course, "course_id", courseId)

	if course.ID == 0 {
		return &course, errors.New("course not found")
	}

	return &course, nil
}

func GetCourseByCode(code string, preload ...string) (*models.Course, error) {
	var course models.Course

	models.DBConfig(models.DB, preload).First(&course, "course_code", code)

	if course.ID == 0 {
		return &course, errors.New("course not found")
	}

	return &course, nil
}

func GetCourseByIdOrCode(course string, preload ...string) (*models.Course, error) {
	if utils.IsValidUUID(course) {
		return GetCourseById(course, preload...)
	}

	return GetCourseByCode(course, preload...)
}

func GetCourses() (*[]models.Course, error) {

	var courses []models.Course

	result := models.DB.Find(&courses)

	if result.Error != nil {
		return &courses, result.Error
	}

	return &courses, nil
}

func UpdateCourse(course string, req *models.Course) (*models.Course, error) {
	response, err := GetCourseByIdOrCode(course)

	if err != nil {
		return req, err
	}

	response.CourseName = req.CourseName
	response.CourseCode = req.CourseCode
	response.Workload = req.Workload

	update := models.DB.Save(response)

	if update.Error != nil {
		return req, update.Error
	}

	return response, nil

}

func CreatePreRequisite(d string, req *[]models.Course) (*models.Course, error) {
	course, err := GetCourseByIdOrCode(d)

	if err != nil {
		return &models.Course{}, err
	}

	course.PreRequisiteCourses = req

	update := models.DB.Save(course)

	if update.Error != nil {
		return &models.Course{}, update.Error
	}

	return course, nil
}

func CreateEquivalent(d string, req *[]models.Course) (*models.Course, error) {
	course, err := GetCourseByIdOrCode(d)

	if err != nil {
		return &models.Course{}, err
	}

	course.EquivalentCourses = req

	update := models.DB.Save(course)

	if update.Error != nil {
		return &models.Course{}, update.Error
	}

	return course, nil
}

func DeleteCourse(course string) error {
	get, err := GetCourseByIdOrCode(course)

	if err != nil {
		return err
	}

	delete := models.DB.Delete(get)

	if delete.Error != nil {
		return delete.Error
	}

	return nil
}
