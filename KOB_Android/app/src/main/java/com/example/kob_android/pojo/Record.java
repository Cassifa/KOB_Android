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
    private Integer aId;
    private Integer ASx;
    private Integer ASy;
    private Integer BId;
    private Integer BSx;
    private Integer BSy;
    private String ASteps;
    private String BSteps;
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
        return aId;
    }

    public void setAid(Integer aid) {
        this.aId = aid;
    }

    public Integer getAsx() {
        return ASx;
    }

    public void setAsx(Integer asx) {
        this.ASx = asx;
    }

    public Integer getAsy() {
        return ASy;
    }

    public void setAsy(Integer asy) {
        this.ASy = asy;
    }

    public Integer getBid() {
        return BId;
    }

    public void setBid(Integer bid) {
        this.BId = bid;
    }

    public Integer getBsx() {
        return BSx;
    }

    public void setBsx(Integer bsx) {
        this.BSx = bsx;
    }

    public Integer getBsy() {
        return BSy;
    }

    public void setBsy(Integer bsy) {
        this.BSy = bsy;
    }

    public String getAsteps() {
        return ASteps;
    }

    public void setAsteps(String asteps) {
        this.ASteps = asteps;
    }

    public String getBsteps() {
        return BSteps;
    }

    public void setBsteps(String bsteps) {
        this.BSteps = bsteps;
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
