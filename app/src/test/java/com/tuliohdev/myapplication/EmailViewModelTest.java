package com.tuliohdev.myapplication;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmailViewModelTest {

    private final String EMAIL = "tulio@zup.com.br";

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private EmailValidator emailValidatorMock;
    @Mock
    private ResourceManager resourceManagerMock;

    @Rule
    public TestRule liveDataTestRule = new InstantTaskExecutorRule();

    private EmailViewModel emailViewModel;

    @Before
    public void setUp() {
        emailViewModel = new EmailViewModel(emailValidatorMock, userRepositoryMock, resourceManagerMock);
    }

    @Test
    public void changeEmailWhenEmailIsValid() {
        // Given
        when(emailValidatorMock.isValidEmail(EMAIL)).thenReturn(true);
        when(userRepositoryMock.changeUserEmail(EMAIL)).thenReturn(Completable.complete());

        // When
        emailViewModel.changeEmail(EMAIL);

        // Then
        assertEquals(Screen.X, emailViewModel.getNextScreenLiveData().getValue());
    }

    @Test
    public void changeEmailWhenEmailIsInvalid() {
        // Given
        final String invalidEmail = "aaaa";
        when(emailValidatorMock.isValidEmail(EMAIL)).thenReturn(false);
        when(userRepositoryMock.changeUserEmail(EMAIL)).thenReturn(Completable.complete());
        when(resourceManagerMock.getString(R.string.invalid_email)).thenReturn(invalidEmail);

        // When
        emailViewModel.changeEmail(EMAIL);

        // Then
        assertEquals(invalidEmail, emailViewModel.getInvalidEmailLiveData().getValue());
    }

    @Test
    public void checkIfOnClearedWillCallCompositeDisposable() {
        // Given
        final CompositeDisposable compositeDisposable = mock(CompositeDisposable.class);
        Whitebox.setInternalState(emailViewModel, "compositeDisposable", compositeDisposable);

        // When
        emailViewModel.onCleared();

        // Then
        verify(compositeDisposable, times(1)).dispose();
        verify(compositeDisposable, times(1)).clear();
    }
}