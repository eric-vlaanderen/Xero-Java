package com.xero.api;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class XeroExceptionsTest {

    private AutoCloseable closeable;
    @InjectMocks
    private static XeroApiExceptionHandler xeroApiExceptionHandler;
    @Mock
    private com.xero.models.accounting.Error error;
    @Mock
    private HttpResponseException httpResponseException;
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private com.xero.models.payrolluk.Problem problem;

    private final String objectType = "objectType";

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testXeroBadRequestException() {
        Exception e = new Exception();
        // XeroBadRequestException extends XeroException so we can catch either
        XeroException exception = assertThrows(
            XeroException.class,
            () ->
                xeroApiExceptionHandler.validationError(objectType, error, e)
        );
        assertEquals(new XeroBadRequestException(objectType, error, e), exception);


    }

    @Test
    public void testXeroMethodNotAllowedException() {
        Exception e = new Exception();
        // XeroMethodNotAllowedException extends XeroException so we can catch either
        XeroException exception = assertThrows(
            XeroException.class,
            () ->
                xeroApiExceptionHandler.validationError(405, objectType, problem, e)
        );
        assertEquals(new XeroMethodNotAllowedException(objectType, problem, e), exception);
    }

    @Test
    public void testXeroUnauthorizedException() {
        int statusCode = 401;
        String message = "Unauthorized - check your scopes and confirm access to this resource";

        // XeroUnauthorizedException extends XeroAuthenticationException and XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroUnauthorizedException exception = assertThrows(
            XeroUnauthorizedException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroForbiddenException() {
        int statusCode = 403;
        String message = "Forbidden - authentication unsuccessful";

        // XeroForbiddenException extends XeroAuthenticationException and XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroForbiddenException exception = assertThrows(
            XeroForbiddenException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroNotFoundException() {
        int statusCode = 404;
        String message = "The resource you're looking for cannot be found";

        // XeroNotFoundException extends XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroNotFoundException exception = assertThrows(
            XeroNotFoundException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroRateLimitException() {
        int statusCode = 429;
        String message = "You've exceeded the per [any] rate limit";

        // XeroRateLimitException extends XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        when(httpHeaders.getFirstHeaderStringValue("X-Rate-Limit-Problem")).thenReturn("[any]");
        when(httpResponseException.getHeaders()).thenReturn(httpHeaders);
        XeroRateLimitException exception = assertThrows(
            XeroRateLimitException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());

    }

    @Test
    public void testXeroMinuteRateLimitException() {
        int statusCode = 429;
        String message = "You've exceeded the per [minute] rate limit";

        // XeroMinuteRateLimitException extends XeroRateLimitException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        when(httpHeaders.getFirstHeaderStringValue("X-Rate-Limit-Problem")).thenReturn("[minute]");
        when(httpHeaders.getFirstHeaderStringValue("X-MinLimit-Remaining")).thenReturn("0");
        when(httpHeaders.getFirstHeaderStringValue("X-DayLimit-Remaining")).thenReturn("10");
        when(httpHeaders.getFirstHeaderStringValue("X-AppMinLimit-Remaining")).thenReturn("10");
        when(httpResponseException.getHeaders()).thenReturn(httpHeaders);
        XeroMinuteRateLimitException exception = assertThrows(
            XeroMinuteRateLimitException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroDailyRateLimitException() {
        int statusCode = 429;
        String message = "You've exceeded the per [day] rate limit";

        // XeroDailyRateLimitException extends XeroRateLimitException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        when(httpResponseException.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getFirstHeaderStringValue("X-Rate-Limit-Problem")).thenReturn("[day]");
        when(httpHeaders.getFirstHeaderStringValue("X-MinLimit-Remaining")).thenReturn("10");
        when(httpHeaders.getFirstHeaderStringValue("X-DayLimit-Remaining")).thenReturn("0");
        when(httpHeaders.getFirstHeaderStringValue("X-AppMinLimit-Remaining")).thenReturn("10");
        XeroDailyRateLimitException exception = assertThrows(
            XeroDailyRateLimitException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroAppMinuteRateLimitException() {
        int statusCode = 429;
        String message = "You've exceeded the per [app] rate limit";

        // XeroAppMinuteRateLimitException extends XeroRateLimitException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        when(httpResponseException.getHeaders()).thenReturn(httpHeaders);
        when(httpHeaders.getFirstHeaderStringValue("X-Rate-Limit-Problem")).thenReturn("[app]");
        when(httpHeaders.getFirstHeaderStringValue("X-MinLimit-Remaining")).thenReturn("10");
        when(httpHeaders.getFirstHeaderStringValue("X-DayLimit-Remaining")).thenReturn("10");
        when(httpHeaders.getFirstHeaderStringValue("X-AppMinLimit-Remaining")).thenReturn("0");
        XeroAppMinuteRateLimitException exception = assertThrows(
            XeroAppMinuteRateLimitException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroServerErrorException() {
        int statusCode = 500;
        String message = "An error occurred in Xero. Check the API Status page http://status.developer.xero.com for current service status.";

        // XeroServerErrorException extends XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroServerErrorException exception = assertThrows(
            XeroServerErrorException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }

    @Test
    public void testXeroNotImplementedException() {
        int statusCode = 501;
        String message = "The method you have called has not been implemented";

        // XeroNotImplementedException extends XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroNotImplementedException exception = assertThrows(
            XeroNotImplementedException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(message, exception.getMessage());
    }


    @Test
    public void testXeroNotAvailableException() {
        // 3 different messages can be returned with status code 503 so we won't check them
        int statusCode = 503;

        // XeroNotAvailableException extends XeroException so we can catch either
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroNotAvailableException exception = assertThrows(
            XeroNotAvailableException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(statusCode, exception.getStatusCode());
    }

    @Test
    public void testXeroApiException() {
        int statusCode = 1;
        String message = "Unknown http status code 1";

        // XeroApiException extends XeroException so we can catch either
        when(httpResponseException.getStatusMessage()).thenReturn(message);
        when(httpResponseException.getStatusCode()).thenReturn(statusCode);
        XeroApiException exception = assertThrows(
            XeroApiException.class,
            () ->
                xeroApiExceptionHandler.execute(httpResponseException)
        );
        assertEquals(message, exception.getMessage());
    }

}

