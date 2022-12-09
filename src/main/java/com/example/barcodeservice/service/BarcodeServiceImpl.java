package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.Barcode;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.repository.BarcodeRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BarcodeServiceImpl implements BarcodeService{

    private final ModelMapper modelMapper;
    private final BarcodeRepository barcodeRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 바코드 저장
     * @return code
     */
    @Override
    public BarcodeCreateResponseDto createCode(BarcodeCreateDto barcodeCreateDto){
        String randomCode = RandomStringUtils.randomNumeric(10);
        barcodeCreateDto.setBarcode(randomCode);
        Barcode barcodeEntity = modelMapper.map(barcodeCreateDto, Barcode.class);

        //중복검증
        List<Barcode> barcodeList = barcodeRepository.findByUserId(barcodeEntity.getUserId());

        if(!barcodeList.isEmpty() && barcodeList.size() > 1) throw new IllegalStateException("여러개의 바코드가 존재합니다.");
        else if(!barcodeList.isEmpty()){
            BarcodeCreateResponseDto barcodeResDto = modelMapper.map(barcodeList.get(0), BarcodeCreateResponseDto.class);
            return barcodeResDto;
        }else{
            barcodeRepository.save(barcodeEntity);
        }

        BarcodeCreateResponseDto rtnBarcodeResponse = modelMapper.map(barcodeEntity, BarcodeCreateResponseDto.class);

        return rtnBarcodeResponse;
    }



}
