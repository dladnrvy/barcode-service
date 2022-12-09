package com.example.barcodeservice.repository;

import com.example.barcodeservice.domain.Barcode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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

@ExtendWith(SpringExtension.class)
@Transactional
@DataJpaTest
class BarcodeRepositoryTest {

    @Autowired
    private BarcodeRepository barcodeRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Test
    public void 바코드_저장_테스트(){
        // given
        Barcode barcode = Barcode.builder()
                .barcode("123456789")
                .userId(123456L)
                .build();

        // when
        Barcode saveBarcode = barcodeRepository.save(barcode);
        Long saveId = saveBarcode.getId();

        Optional<Barcode> findId = barcodeRepository.findById(saveId);
        Long findUserId = findId.get().getUserId();
        String findBarCode = findId.get().getBarcode();

        // then
        Assertions.assertThat(findUserId).isEqualTo(barcode.getUserId());
        Assertions.assertThat(findBarCode).isEqualTo(barcode.getBarcode());
    }

    @Test
    public void 바코드_길이_오류_테스트(){
        // given
        Barcode barcode = Barcode.builder()
                .barcode("12345678900")
                .userId(12345678900L)
                .build();

        // when
        // then
        Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("DB에서의 바코드 중복 오류체크")
    @Test
    void 바코드_중복_테스트_오류체크() {
        Barcode barcode1, barcode2;

        barcode1 = new Barcode(1L,123456789L,"5767844471");
        barcode2 = new Barcode(2L,123456788L,"5767844471");
        barcodeRepository.save(barcode1);
        Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @DisplayName("DB에서의 회원아이디 중복 오류체크")
    @Test
    void 회원아이디_중복_테스트_오류체크() {
        Barcode barcode1, barcode2;

        barcode1 = new Barcode(1L,123456788L,"5767844471");
        barcode2 = new Barcode(2L,123456788L,"5767844472");
        barcodeRepository.save(barcode1);
        Assertions.assertThatThrownBy(()-> barcodeRepository.save(barcode2)).isInstanceOf(DataIntegrityViolationException.class);
    }



}