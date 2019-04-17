package com.tuliohdev.myapplication.withrx;

import com.tuliohdev.myapplication.CreditCardEntity;
import com.tuliohdev.myapplication.Cryptograph;
import com.tuliohdev.myapplication.NetworkUtils;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Cryptograph.class, NetworkUtils.class})
public class RxCreditCardRepositoryTest {

    private final String CARD_ID = "123";

    @Mock
    private RxCreditCardApi rxCreditCardApiMock;

    private RxCreditCardRepository rxCreditCardRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        rxCreditCardRepository = new RxCreditCardRepository(rxCreditCardApiMock);
    }

    @Test
    public void getCreditCardByIdWhenSuccess() {
        // Given
        final CreditCardEntity creditCardEntity = mock(CreditCardEntity.class);
        when(rxCreditCardApiMock.getCreditCardById(CARD_ID)).thenReturn(Single.just(creditCardEntity));

        // When
        TestObserver<CreditCardEntity> testObserver = rxCreditCardRepository.getCreditCardById(CARD_ID).test();

        // Then
        testObserver
                .assertComplete()
                .assertValue(creditCardEntity);
    }

    @Test
    public void getCreditCardByIdWhenError() {
        // Given
        final Throwable throwable = new RuntimeException();
        when(rxCreditCardApiMock.getCreditCardById(CARD_ID)).thenReturn(Single.<CreditCardEntity>error(throwable));

        // When
        TestObserver<CreditCardEntity> testObserver = rxCreditCardRepository.getCreditCardById(CARD_ID).test();

        // Then
        testObserver.assertError(throwable);
    }

    @Test
    public void getCreditCardNumberByCardId() {
        // Given
        final String cardNumber = "12351";
        mockStatic(Cryptograph.class);
        mockStatic(NetworkUtils.class);
        when(Cryptograph.decrypt(cardNumber)).thenReturn(cardNumber);
        when(rxCreditCardApiMock.getCreditCardNumberByCardId(CARD_ID)).thenReturn(Single.just(cardNumber));

        // When
        TestObserver<String> testObserver = rxCreditCardRepository.getCreditCardNumberByCardId(CARD_ID).test();

        // Then
        verifyStatic(NetworkUtils.class, times(1));
        NetworkUtils.checkApplicationIntegrity();
        testObserver
                .assertComplete()
                .assertValue(cardNumber);
    }
}