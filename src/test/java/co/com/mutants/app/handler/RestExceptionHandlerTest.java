package co.com.mutants.app.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import co.com.mutants.domain.exception.MutantException;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class RestExceptionHandlerTest {

    private static final String ERROR_CODE = "ERROR_CODE";
    private static final String MESSAGE = "message";

    @Spy
    private RestExceptionHandler subject;

    @Test
    public void shouldHandleAHttpMessageNotReadableException() throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {

        HttpMessageNotReadableException httpMessageNotReadableException =
                new HttpMessageNotReadableException(MESSAGE, mock(HttpInputMessage.class));

        Method declaredMethod = RestExceptionHandler.class.getDeclaredMethod("handleHttpMessageNotReadable",
                HttpMessageNotReadableException.class,
                HttpHeaders.class,
                HttpStatus.class,
                WebRequest.class);
        declaredMethod.setAccessible(true);

        ResponseEntity<Void> responseEntity = (ResponseEntity<Void>) declaredMethod.invoke(subject,
                httpMessageNotReadableException, null, null, null);

        assertNotNull(responseEntity);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void shouldHandleAMethodArgumentNotValidException()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BindingResult bindingResult = mock(BindingResult.class);
        String defaultMessage = "default constraint field error message";
        when(bindingResult.getAllErrors())
                .thenReturn(Arrays.asList(new FieldError("objectName1", "field1", defaultMessage)
                        , new FieldError("objectName2", "field2", defaultMessage)));

        final MethodArgumentNotValidException methodArgumentNotValueException =
                mock(MethodArgumentNotValidException.class);
        when(methodArgumentNotValueException.getBindingResult()).thenReturn(bindingResult);

        Method declaredMethod = RestExceptionHandler.class.getDeclaredMethod("handleMethodArgumentNotValid",
                MethodArgumentNotValidException.class,
                HttpHeaders.class,
                HttpStatus.class,
                WebRequest.class);
        declaredMethod.setAccessible(true);

        ResponseEntity<Void> responseEntity = (ResponseEntity<Void>) declaredMethod
                .invoke(subject, methodArgumentNotValueException, null, null, null);

        assertNotNull(responseEntity);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void shouldHandleAMutantException()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = RestExceptionHandler.class
                .getDeclaredMethod("handleMutantException", MutantException.class);
        declaredMethod.setAccessible(true);

        ResponseEntity<Void> responseEntity = (ResponseEntity<Void>) declaredMethod
                .invoke(subject, new MutantException(MESSAGE, ERROR_CODE));

        assertNotNull(responseEntity);
        assertEquals(FORBIDDEN, responseEntity.getStatusCode());
    }

}