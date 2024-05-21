package com.example.kob_android.net.responseData.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:43
 * @Description:
 */
@ToString
public class User {
    public User(Integer id, String username, String password, Integer rating, String photo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rating = rating;
        this.photo = photo;
    }

    public User() {}

    //表映射成class
    private Integer id;
    private String username;
    private String password;

    private Integer rating;
    private String photo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}