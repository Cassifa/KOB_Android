package com.example.kob_android.pojo;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:41
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:30
 * @Description: 回放列表类
 */
@Data
public class RecordItem {
    public RecordItem() {
    }

    public RecordItem(String a_photo, String a_username, String b_photo, String b_username, Record record, String result) {
        this.a_photo = a_photo;
        this.a_username = a_username;
        this.b_photo = b_photo;
        this.b_username = b_username;
        this.record = record;
        this.result = result;
    }

    private String a_photo;
    private String a_username;
    private String b_photo;
    private String b_username;
    private Record record;
    private String result;

    public String getA_photo() {
        return a_photo;
    }

    public void setA_photo(String a_photo) {
        this.a_photo = a_photo;
    }

    public String getA_username() {
        return a_username;
    }

    public void setA_username(String a_username) {
        this.a_username = a_username;
    }

    public String getB_photo() {
        return b_photo;
    }

    public void setB_photo(String b_photo) {
        this.b_photo = b_photo;
    }

    public String getB_username() {
        return b_username;
    }

    public void setB_username(String b_username) {
        this.b_username = b_username;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
