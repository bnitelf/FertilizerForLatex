package com.example.puicbr.fertilizerforlatex.model.Constants;

/**
 * Created by Folder on 01-May-17.
 */
public enum FertilizingRoundState {
    DONE("ใส่แล้ว"),
    NOT_DONE("ยังไม่ใส่");

    private final String description;

    private FertilizingRoundState(String description) {
        this.description = description;
    }

    public String getFieldDescription() {
        return description;
    }
}
