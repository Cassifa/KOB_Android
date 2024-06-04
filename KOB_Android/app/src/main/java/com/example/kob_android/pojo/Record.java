package com.example.kob_android.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Cassifa
 * @CreateTime: 2024-04-17  21:34
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    private Integer id;
    private Integer aid;
    private Integer asx;
    private Integer asy;
    private Integer bid;
    private Integer bsx;
    private Integer bsy;
    private String asteps;
    private String bsteps;
    private String map;
    private String loser;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;

    public String getMap(){
        return map;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getAsx() {
        return asx;
    }

    public void setAsx(Integer asx) {
        this.asx = asx;
    }

    public Integer getAsy() {
        return asy;
    }

    public void setAsy(Integer asy) {
        this.asy = asy;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getBsx() {
        return bsx;
    }

    public void setBsx(Integer bsx) {
        this.bsx = bsx;
    }

    public Integer getBsy() {
        return bsy;
    }

    public void setBsy(Integer bsy) {
        this.bsy = bsy;
    }

    public String getAsteps() {
        return asteps;
    }

    public void setAsteps(String asteps) {
        this.asteps = asteps;
    }

    public String getBsteps() {
        return bsteps;
    }

    public void setBsteps(String bsteps) {
        this.bsteps = bsteps;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
