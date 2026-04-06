package org.example.rest.context;

import org.example.rest.common.HttpHeaders;
import org.example.rest.enums.HttpStatus;
import org.example.rest.exception.ResponseContextException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class ResponseContext {

    private HttpStatus status;

    private HttpHeaders headers;

    private byte[] responseBody;

    public static ResponseContext build(HttpStatus status) {
        return build(status, null, (String) null);
    }

    public static ResponseContext build(HttpStatus status, HttpHeaders headers) {
        return build(status, headers, (String) null);
    }

    public static ResponseContext build(HttpStatus status, HttpHeaders headers, byte[] responseBody) {
        return buildWithBytes(status, headers, responseBody == null ? null : responseBody);
    }


    public static ResponseContext build(HttpStatus status, HttpHeaders headers, String responseBody) {
        return buildWithBytes(status, headers, responseBody == null ? null : responseBody.getBytes());
    }

    public static ResponseContext buildWithBytes(HttpStatus status, HttpHeaders headers, byte[] responseBody) {
        var context = new ResponseContext();
        context.setStatus(status);
        context.setHeaders(headers);
        context.setResponseBody(responseBody);

        return context;
    }

    public byte[] getResponseAsBytes() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            outputStream.write(("HTTP/1.1 " + status.getCode() + " " + status.getReasonPhrase() + "\r\n").getBytes());

            if (headers != null) {
                outputStream.write(headers.toString().getBytes());
            }

            outputStream.write("\r\n".getBytes());

            if (responseBody != null) {
                outputStream.write(responseBody);
            }

            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new ResponseContextException("Exception trying to get bytes for response", ex);
        }
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = Objects.requireNonNull(status);
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(byte[] responseBody) {
        this.responseBody = responseBody;
    }
}