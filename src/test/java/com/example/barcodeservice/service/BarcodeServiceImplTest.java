package com.example.barcodeservice.service;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.dto.BarcodeCreateResponseDto;
import com.example.barcodeservice.repository.BarcodeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BarcodeServiceImplTest {

    @Mock
    private BarcodeRepository barcodeRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private BarcodeServiceImpl barcodeService;


    private static Logger log = LoggerFactory.getLogger(BarcodeServiceImplTest.class);
    private String BARCODE = "123";
    private Long USER_ID = 1L;
    private String FAIL_BARCODE = "567";
    private Long FAIL_USER_ID = 2L;

    private BarcodeEntity barcodeEntity = BarcodeEntity.builder().barcode(BARCODE).userId(USER_ID).build();
    private BarcodeCreateDto barcodeCreateDto = new BarcodeCreateDto();

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {
        @Test
        void 바코드_저장() {
            // given
            barcodeCreateDto.setUserId(USER_ID);

            // mocking
            given(barcodeRepository.findByUserId(any())).willReturn(null);
            given(barcodeRepository.save(any())).willReturn(barcodeEntity);

            // when
            BarcodeCreateResponseDto barcodeResponse = barcodeService.createCode(barcodeCreateDto);

            // then
            assertThat(barcodeResponse).isNotNull();
        }

        @Test
        void 기존바코드_리턴() {
            // given
            barcodeCreateDto.setUserId(USER_ID);

            // mocking
            given(barcodeRepository.findByUserId(any())).willReturn(barcodeEntity);

            // when
            BarcodeCreateResponseDto barcodeResponse = barcodeService.createCode(barcodeCreateDto);

            // then
            assertThat(barcodeResponse.getBarcode()).isEqualTo(BARCODE);
        }

        @Test
        void 바코드_번호를_통한_조회() {
            // given
            // mocking
            given(barcodeRepository.findByBarcode(any())).willReturn(barcodeEntity);

            // when
            Optional<BarcodeEntity> barcodeResponse = barcodeService.getBarcodeByCode(BARCODE);

            // then
            assertThat(barcodeResponse).isNotNull();
            assertThat(barcodeResponse.get().getBarcode()).isEqualTo(BARCODE);
            assertThat(barcodeResponse.get().getUserId()).isEqualTo(USER_ID);
        }

        @Test
        void 회원_번호를_통한_조회() {
            // given
            // mocking
            given(barcodeRepository.findByUserId(any())).willReturn(barcodeEntity);

            // when
            Optional<BarcodeEntity> barcodeResponse = barcodeService.getBarcodeByUserId(USER_ID);

            // then
            assertThat(barcodeResponse).isNotNull();
            assertThat(barcodeResponse.get().getBarcode()).isEqualTo(BARCODE);
            assertThat(barcodeResponse.get().getUserId()).isEqualTo(USER_ID);
        }

    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        void 바코드_저장() {
            // given
            barcodeCreateDto.setUserId(USER_ID);

            // mocking
            given(barcodeRepository.findByUserId(any())).willReturn(null);
            given(barcodeRepository.save(any())).willReturn(null);

            // when & then
            Assertions.assertThatThrownBy(() -> barcodeService.createCode(barcodeCreateDto)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 바코드_번호를_통한_조회() {
            // given
            // mocking
            given(barcodeRepository.findByBarcode(any())).willReturn(null);

            // when
            Optional<BarcodeEntity> barcodeResponse = barcodeService.getBarcodeByCode(BARCODE);

            // then
            assertThat(barcodeResponse).isEmpty();
        }

        @Test
        void 회원_번호를_통한_조회() {
            // given
            // mocking
            given(barcodeRepository.findByUserId(any())).willReturn(null);

            // when
            Optional<BarcodeEntity> barcodeResponse = barcodeService.getBarcodeByUserId(USER_ID);

            // then
            assertThat(barcodeResponse).isEmpty();
        }
    }

}