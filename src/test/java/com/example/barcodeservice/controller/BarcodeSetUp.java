package com.example.barcodeservice.controller;


import com.example.barcodeservice.domain.BarcodeEntity;
import com.example.barcodeservice.dto.BarcodeCreateDto;
import com.example.barcodeservice.repository.BarcodeRepository;
import com.example.barcodeservice.service.BarcodeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class BarcodeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BarcodeSetUp setUp;

    @Nested
    @DisplayName("성공케이스")
    class SuccessCase {
        @Test
        void 바코드생성() throws Exception {
            // given
            BarcodeCreateDto barcodeCreateDto = new BarcodeCreateDto();
            barcodeCreateDto.setUserId(123l);
            barcodeCreateDto.setBarcode("456");
            setUp.saveBarcode(barcodeCreateDto);

            // when
            ResultActions resultActions = mockMvc.perform(post("/barcode/createCode")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(barcodeCreateDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk());
        }

        @Test
        void 바코드아이디_조회() throws Exception {
            // given
            BarcodeCreateDto barcodeCreateDto = new BarcodeCreateDto();
            barcodeCreateDto.setUserId(123l);
            barcodeCreateDto.setBarcode("456");
            setUp.saveBarcode(barcodeCreateDto);

            // when
            ResultActions resultActions = mockMvc.perform(get("/barcode/createCode")
                            .param("barcode","456"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("data", is(notNullValue())));
        }
    }

    @Nested
    @DisplayName("실패케이스")
    class FailCase {
        @Test
        void Validated_바코드생성_userId_NULL() throws Exception {
            // given
            BarcodeCreateDto barcodeCreateDto = new BarcodeCreateDto();
            //barcodeCreateDto.setUserId(123l);
            barcodeCreateDto.setBarcode("4567");

            // when
            ResultActions resultActions = mockMvc.perform(post("/barcode/createCode")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(barcodeCreateDto))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isBadRequest());
        }

        @Test
        void 바코드아이디_조회() throws Exception {
            // given
            BarcodeCreateDto barcodeCreateDto = new BarcodeCreateDto();
            barcodeCreateDto.setUserId(123l);
            barcodeCreateDto.setBarcode("456");
            setUp.saveBarcode(barcodeCreateDto);

            // when
            ResultActions resultActions = mockMvc.perform(get("/barcode/createCode")
                            .param("barcode","111"))
                    .andDo(print());

            // then
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("data").value(""));
        }
    }


}
@Component
public class BarcodeSetUp{
    @Autowired
    private BarcodeRepository barcodeRepository;

    public void saveBarcode(BarcodeCreateDto barcodeCreateDto){
        BarcodeEntity barcode = BarcodeEntity.builder()
                .userId(barcodeCreateDto.getUserId())
                .barcode(barcodeCreateDto.getBarcode())
                .build();
        barcodeRepository.save(barcode);
    }
}
