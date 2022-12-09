package com.example.barcodeservice.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;



@Data
@ToString
public class BarcodeCreateRequestDto {

    @NotNull(message = "USERID_IS_MANDATORY")
    @Digits(integer = 9, fraction = 0) //최대 9자리로 정해두되 9자리보다 짧을경우도 존재할것같아 조건엔 추가하지 않음
    private Long userId;
}
