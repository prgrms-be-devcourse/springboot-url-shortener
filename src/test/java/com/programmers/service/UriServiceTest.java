package com.programmers.service;

import com.programmers.component.UriConverter;
import com.programmers.entity.UriEntity;
import com.programmers.model.CreateResponse;
import com.programmers.repository.UriRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UriServiceTest {

    @Mock
    private UriRepository uriRepository;

    @Mock
    private UriConverter uriConverter;

    @InjectMocks
    private UriService uriService;

    @DisplayName("URI 형식의 문자열을 입력하면 short uri를 반환한다.")
    @Test
    void createShortUriTest() {
        //Given
        String inputUri = "https://www.naver.com";
        UriEntity resultEntity = new UriEntity(1L, inputUri);

        when(uriRepository.findByOriginalUriEquals(inputUri)).thenReturn(Optional.empty());
        when(uriRepository.save(any())).thenReturn(resultEntity);
        when(uriConverter.convertCreateResponseFrom(any(UriEntity.class)))
                .thenAnswer(invocation -> {
                    UriEntity argument = invocation.getArgument(0);
                    return new CreateResponse(argument.getShortUri(), argument.getCount());
                });
        //When
        CreateResponse createResponse = uriService.createShortUri(inputUri);

        //Then
        assertThat(createResponse.getShortUri()).isNotNull();
        assertThat(createResponse.getCount()).isEqualTo(0);
    }

    @DisplayName("등록되어있는 shortUri를 통해 uri를 가져올 수 있다.")
    @Test
    void getOriginalUriTest() {
        //Given
        String shortUri = "0xabcde";
        String originalUri = "https://www.naver.com";
        UriEntity resultEntity = new UriEntity(1L, originalUri, shortUri);
        when(uriRepository.findByShortUriEquals(shortUri)).thenReturn(Optional.of(resultEntity));

        //When
        String findOriginalUri = uriService.getOriginalUri(shortUri);

        //Then
        assertThat(findOriginalUri).isEqualTo(originalUri);
    }

}