package com.tuliohdev.myapplication.withoutrx;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import com.tuliohdev.myapplication.CreditCardEntity;
import com.tuliohdev.myapplication.withoutrx.CreditCardApi;
import com.tuliohdev.myapplication.withoutrx.CreditCardRepository;
import com.tuliohdev.myapplication.withoutrx.ResponseCallback;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreditCardRepositoryTest {

    private final String CARD_ID = "123";

    @Rule
    public TestRule liveDataTestRule = new InstantTaskExecutorRule();

    @Mock
    private CreditCardApi creditCardApiMock;
    @Mock
    private Call<CreditCardEntity> retrofitCallMock;

    @Captor
    private ArgumentCaptor<Callback<CreditCardEntity>> retrofitCallbackArgumentCaptor;

    private CreditCardRepository creditCardRepository;

    @Before
    public void setUp() {
        creditCardRepository = new CreditCardRepository(creditCardApiMock);

        when(creditCardApiMock.getCreditCardById(CARD_ID)).thenReturn(retrofitCallMock);
    }

    @Test
    public void getCreditCardWhenReturnSuccess() {
        // Given
        final CreditCardEntity creditCardEntity = mock(CreditCardEntity.class);
        final Response<CreditCardEntity> retrofitResponse = Response.success(creditCardEntity);

        // When
        LiveData<ResponseCallback<CreditCardEntity>> response = creditCardRepository.getCreditCardById(CARD_ID);
        verify(retrofitCallMock).enqueue(retrofitCallbackArgumentCaptor.capture());
        retrofitCallbackArgumentCaptor.getValue().onResponse(retrofitCallMock, retrofitResponse);

        // Then
        assertEquals(creditCardEntity, response.getValue().getResponse());
    }

    @Test
    public void getCreditCardWhenReturnError() {
        // Given
        final Throwable throwable = new RuntimeException();

        // When
        LiveData<ResponseCallback<CreditCardEntity>> response = creditCardRepository.getCreditCardById(CARD_ID);
        verify(retrofitCallMock).enqueue(retrofitCallbackArgumentCaptor.capture());
        retrofitCallbackArgumentCaptor.getValue().onFailure(retrofitCallMock, throwable);

        // Then
        assertEquals(throwable, response.getValue().getError());
    }
}