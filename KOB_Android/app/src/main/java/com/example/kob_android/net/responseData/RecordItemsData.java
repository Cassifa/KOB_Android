package com.example.kob_android.net.responseData;

import com.example.kob_android.pojo.RecordItem;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:42
 * @Description:
 *//**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:30
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordItemsData {
    private LinkedList<RecordItem> records;
    private Integer record_count;

    public LinkedList<RecordItem> getRecords() {
        return records;
    }

    public void setRecords(LinkedList<RecordItem> records) {
        this.records = records;
    }

    public Integer getRecord_count() {
        return record_count;
    }

    public void setRecord_count(Integer record_count) {
        this.record_count = record_count;
    }
}
