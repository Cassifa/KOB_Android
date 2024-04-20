package com.example.kob_android.net.responseData;

import com.example.kob_android.net.responseData.pojo.User;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankListData {
    private LinkedList<User> users;
    private Integer users_count;

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }
    public LinkedList<User> getUsers(){
        return  users;
    }

    public Integer getUsers_count() {
        return users_count;
    }

    public void setUsers_count(Integer users_count) {
        this.users_count = users_count;
    }
}
