package com.programmers.controller;

import com.programmers.model.CreateResponse;
import com.programmers.service.UriService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UriController.class)
class UriControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UriService uriService;

    @DisplayName("생성되지 않은 shortUri로는 접근이 불가하다.")
    @Test
    void getUriUsingInvalidShortUri() throws Exception {
        //Given
        String falseShortUri = "28x8332";
        //When
        when(uriService.getOriginalUri(falseShortUri)).thenThrow(NoSuchElementException.class);
        //Then
        mockMvc.perform(get("/{shortUri}", falseShortUri))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @DisplayName("처음 서버를 작동하면 초기 화면으로 이동한다.")
    @Test
    void startWithHomePage() throws Exception {
        //Given //When
        //Then
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @DisplayName("uri를 보내면 shortUri를 만들고 result 화면으로 이동한다.")
    @Test
    void makeShortUri() throws Exception {
        //Given
        String validUri = "https://www.naver.com";
        //When
        when(uriService.createShortUri(validUri)).thenReturn(new CreateResponse("1234jdb", 0));

        //Then
        mockMvc.perform(post("/api").param("uri", validUri))
                .andExpect(status().isOk())
                .andExpect(view().name("result"));
     }

    @DisplayName("잘못된 형식의 uri를 보내면 error가 발생한다.")
    @Test
    void makeShortUriUsingInvalidUri() throws Exception {
        //Given
        String invalidUri = "wwww.naver.com";
        //When //Then
        mockMvc.perform(post("/api").param("uri", invalidUri))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }
}