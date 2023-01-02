package com.example.barcodeservice.repository;

import com.example.barcodeservice.domain.BarcodeEntity;
import org.apache.commons.lang.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class BarcodeRepositoryTest {

    @Autowired
    private BarcodeRepository barcodeRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
    private BarcodeEntity barcodeEntity;
    private Long userId = 123l;
    private String barcode = "test";

    @BeforeEach
    void setUp() {
        barcodeEntity = BarcodeEntity.builder()
                .barcode(barcode)
                .userId(userId)
                .build();
    }

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {
        @Test
        void 바코드_저장(){
            // given
            BarcodeEntity saveBarcode = barcodeRepository.save(barcodeEntity);
            Long saveId = saveBarcode.getBarcodeId();

            // when
            Optional<BarcodeEntity> findId = barcodeRepository.findById(saveId);
            Long findUserId = findId.get().getUserId();
            String findBarCode = findId.get().getBarcode();

            // then
            Assertions.assertThat(findUserId).isEqualTo(barcodeEntity.getUserId());
            Assertions.assertThat(findBarCode).isEqualTo(barcodeEntity.getBarcode());
        }

        @Test
        void userId를_통한_조회(){
            // given
            BarcodeEntity saveBarcode = barcodeRepository.save(barcodeEntity);
            Long saveUserId = saveBarcode.getUserId();

            // when
            BarcodeEntity findBarcode = barcodeRepository.findByUserId(saveUserId);
            Long findUserId = findBarcode.getUserId();
            String findBarCode = findBarcode.getBarcode();

            // then
            Assertions.assertThat(findUserId).isEqualTo(barcodeEntity.getUserId());
            Assertions.assertThat(findBarCode).isEqualTo(barcodeEntity.getBarcode());
        }

        @Test
        void barcode를_통한_조회(){
            // given
            BarcodeEntity barcode = barcodeRepository.save(barcodeEntity);
            String saveBarcode = barcode.getBarcode();

            // when
            BarcodeEntity findBarcode = barcodeRepository.findByBarcode(saveBarcode);
            Long findUserId = findBarcode.getUserId();
            String findBarCode = findBarcode.getBarcode();

            // then
            Assertions.assertThat(findUserId).isEqualTo(barcodeEntity.getUserId());
            Assertions.assertThat(findBarCode).isEqualTo(barcodeEntity.getBarcode());
        }

    }
    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        void 바코드_저장_barcode_NULL(){
            // given
            barcodeEntity = BarcodeEntity.builder()
                    //.barcode(barcode)
                    .userId(userId)
                    .build();
            // when & then
            Assertions.assertThatThrownBy(() -> barcodeRepository.save(barcodeEntity)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 바코드_저장_userId_NULL(){
            // given
            barcodeEntity = BarcodeEntity.builder()
                    .barcode(barcode)
                    //.userId(userId)
                    .build();
            // when & then
            Assertions.assertThatThrownBy(() -> barcodeRepository.save(barcodeEntity)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 바코드_길이_오류(){
            // given
            BarcodeEntity barcode = BarcodeEntity.builder()
                    .barcode("1234567890000000000000")
                    .userId(12345678900L)
                    .build();

            // when
            // then
            Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 바코드_중복() {
            // given
            BarcodeEntity barcode1, barcode2;
            barcode1 = new BarcodeEntity(1L,123456789L,"5767844471");
            barcode2 = new BarcodeEntity(2L,123456788L,"5767844471");
            // when
            barcodeRepository.save(barcode1);
            // then
            Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode2)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void 회원아이디_중복() {
            // given
            BarcodeEntity barcode1, barcode2;
            barcode1 = new BarcodeEntity(1L,123456788L,"5767844471");
            barcode2 = new BarcodeEntity(2L,123456788L,"5767844472");
            // when
            barcodeRepository.save(barcode1);
            // then
            Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode2)).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        void userId를_통한_조회(){
            // given
            Long failUserId = 1l;
            // when
            Optional<BarcodeEntity> findBarcode = Optional.ofNullable(barcodeRepository.findByUserId(failUserId));
            // then
            Assertions.assertThat(findBarcode).isEmpty();
        }

        @Test
        void barcode를_통한_조회(){
            // given
            String failBarcode = "1";
            // when
            Optional<BarcodeEntity> findBarcode = Optional.ofNullable(barcodeRepository.findByBarcode(failBarcode));
            // then
            Assertions.assertThat(findBarcode).isEmpty();
        }
    }








}