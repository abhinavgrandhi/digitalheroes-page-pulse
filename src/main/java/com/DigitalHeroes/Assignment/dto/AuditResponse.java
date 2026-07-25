package com.DigitalHeroes.Assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditResponse {

    private int status;
    private long responseTime;
    private String title;
    private String metaDescription;
    private int h1Count;
    private int imagesWithoutAlt;
    private int wordCount;

}