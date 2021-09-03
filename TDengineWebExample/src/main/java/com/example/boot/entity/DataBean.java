package com.example.boot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data // get and set
public class DataBean {
    private String tgaName;
    private String timestamp;
    private String valueDouble;

    public DataBean(String tgaName, String timestamp) {
        this.tgaName = tgaName;
        this.timestamp = timestamp;
    }
}