package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.Barcode;
import com.example.barcodeservice.dto.BarcodeCreateRequestDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.repository.BarcodeRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@ExtendWith(MockitoExtension.class)
@Transactional
@SpringBootTest
class BarcodeServiceImplTest {

    @Autowired ModelMapper modelMapper;
    @InjectMocks
    BarcodeServiceImpl barcodeServiceImpl;

    private static Logger log = LoggerFactory.getLogger(BarcodeServiceImplTest.class);

    @Test
    void 바코드_생성_테스트() {
        Long userId = 123456789L;
        String randomCode = RandomStringUtils.randomNumeric(10);

        // given
        Barcode barcodeEntity = Barcode.builder()
                .barcode(randomCode)
                .userId(userId)
                .build();

        // when
        //barcodeRepository.save(barcodeEntity);
        BarcodeCreateResponseDto rtnBarcodeResponse = modelMapper.map(barcodeEntity, BarcodeCreateResponseDto.class);

        // then
        Assertions.assertThat(rtnBarcodeResponse.getBarcode()).isEqualTo(barcodeEntity.getBarcode());
    }


    /**
    ** userId에 따른 중복체크
    **/
    @Test
    void 중복_테스트_사용자아이디(){
        // given
        Barcode barcodeEntity = Barcode.builder()
                .barcode("5767844471")
                .userId(123456789L)
                .build();

        //barcodeRepository.save(barcodeEntity);
        BarcodeCreateRequestDto barcodeReqDto = modelMapper.map(barcodeEntity, BarcodeCreateRequestDto.class);
        //userId로 조회
        //List<Barcode> barcodeList = barcodeRepository.findByUserId(barcodeReqDto.getUserId());

//       if(!barcodeList.isEmpty()){
//            Assertions.assertThat(barcodeEntity.getBarcode()).isEqualTo(barcodeList.get(0).getBarcode());
//        } else {
//            Barcode saveBarcode = barcodeRepository.save(barcodeEntity);
//            Assertions.assertThat(barcodeEntity.getBarcode()).isEqualTo(saveBarcode.getBarcode());
//        }
    }

    /**
     ** 바코드에 따른 중복체크
     **/
    @Test
    void 중복_테스트_바코드(){
        Barcode barcodeEntity = Barcode.builder()
                .barcode("5767844471")
                .userId(123456789L)
                .build();

//        barcodeRepository.save(barcodeEntity);
//        BarcodeCreateRequestDto barcodeReqDto = modelMapper.map(barcodeEntity, BarcodeCreateRequestDto.class);
//        //userId로 조회
//        List<Barcode> barcodeList = barcodeRepository.findByUserId(barcodeReqDto.getUserId());
//
//        if(!barcodeList.isEmpty()){
//            Assertions.assertThat(barcodeEntity.getBarcode()).isEqualTo(barcodeList.get(0).getBarcode());
//        } else {
//            Barcode saveBarcode = barcodeRepository.save(barcodeEntity);
//            Assertions.assertThat(barcodeEntity.getBarcode()).isEqualTo(saveBarcode.getBarcode());
//        }
    }




}