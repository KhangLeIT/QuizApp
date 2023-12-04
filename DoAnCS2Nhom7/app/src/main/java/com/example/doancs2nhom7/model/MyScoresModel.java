package com.example.doancs2nhom7.model;

public class MyScoresModel {
    private String testId;
    private long scoreId;

    public MyScoresModel(String testId, long scoreId) {
        this.testId = testId;
        this.scoreId = scoreId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public long getScoreId() {
        return scoreId;
    }

    public void setScoreId(long scoreId) {
        this.scoreId = scoreId;
    }
}
