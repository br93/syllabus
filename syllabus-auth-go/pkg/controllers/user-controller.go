package controllers

import (
	"net/http"

	"github.com/br93/syllabus/syllabus-auth-go/pkg/models"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/services"
	"github.com/br93/syllabus/syllabus-auth-go/pkg/utils"
	"github.com/gin-gonic/gin"
)

func Signup(c *gin.Context) {

	body := models.UserRequestModel{}

	if c.Bind(&body) != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Failed to read body",
		})

		return
	}

	hash, err := services.HashPassword(body.Password)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Failed to hash password",
		})

		return
	}

	body.Password = services.StringHash(hash)
	user := utils.ToUser(&body)

	if err := services.CreateUser(user); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Failed to create user",
		})

		return
	}

	c.JSON(http.StatusOK, gin.H{})

}

func Login(c *gin.Context) {
	body := models.UserRequestModel{}

	if c.Bind(&body) != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Failed to read body",
		})

		return
	}

	user, err := services.GetUserByEmail(body.Email)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid email or password",
		})

		return
	}

	err = services.CompareHash(user.Password, body.Password)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Invalid email or password",
		})

		return
	}

	tokenString, err := services.GenerateToken(user.ID)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{
			"error": "Failed to create token",
		})

		return
	}

	c.SetSameSite(http.SameSiteLaxMode)
	c.SetCookie("Authorization", tokenString, 3600, "", "", false, true)

	c.JSON(http.StatusOK, gin.H{})
}

func Validate(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "User logged in",
	})
}

func GetMe(c *gin.Context) {
	currentUser := c.MustGet("user").(*models.User)
	c.JSON(http.StatusOK, gin.H{
		"user": utils.ToUserResponse(currentUser),
	})

}
