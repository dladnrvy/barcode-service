package com.example.barcodeservice.controller;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateRequestDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.dto.basic.BasicResponse;
import com.example.barcodeservice.dto.basic.RtnCode;
import com.example.barcodeservice.service.BarcodeServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/barcode")
public class BarcodeController {

    private final BarcodeServiceImpl barcodeServiceImpl;
    private final ModelMapper modelMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @ApiOperation(value="바코드 생성", notes="정상적으로 생성 될 경우 상태 200과 생성된 바코드 리턴 / 적립 중 오류가 발생하면 data에 오류메시지를 리턴 / Validated 오류가 발생하면 400 오류를 리턴")
    @PostMapping("/createCode")
    public ResponseEntity<BasicResponse> createBarcode(@Validated @RequestBody BarcodeCreateRequestDto barcodeReqDto){
        BasicResponse rtn = new BasicResponse<>();
        BarcodeCreateDto barcodeCreateDto = modelMapper.map(barcodeReqDto, BarcodeCreateDto.class);

        BarcodeCreateResponseDto barcodeCreateResponseDto = barcodeServiceImpl.createCode(barcodeCreateDto);
        rtn.setCode(RtnCode.SUCCESS);
        rtn.setData(barcodeCreateResponseDto.getBarcode());
        return ResponseEntity.ok(rtn);
    }

    @ApiOperation(value="바코드 ID 조회",
            notes="정상적으로 조회 될 경우 상태 200과 바코드 ID 리턴 / 적립 중 오류가 발생하면 data에 오류메시지를 리턴")
    @GetMapping("/createCode")
    public BasicResponse getBarcodeId(@RequestParam String barcode){
        BasicResponse basicResponse = new BasicResponse<>();
        Optional<BarcodeEntity> barcodeData = barcodeServiceImpl.getBarcodeByCode(barcode);
        if(barcodeData.isPresent()) basicResponse.setCode(RtnCode.FAIL);
        else {
            basicResponse.setCode(RtnCode.SUCCESS);
            basicResponse.setData(barcodeData.get().getBarcodeId());
        }
        return basicResponse;
    }
}
