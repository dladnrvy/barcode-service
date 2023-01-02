package com.example.barcodeservice.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;



@Data
@ToString
public class BarcodeCreateRequestDto {

    @NotNull(message = "USERID_IS_MANDATORY")
    private Long userId;
}
