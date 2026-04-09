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

    private String contentType = "application/json"; // Добавляем Content-Type по умолчанию


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
        var contex = new ResponseContext();

        contex.setStatus(status);
        contex.setHeaders(headers);
        contex.setResponseBody(responseBody);

        return contex;
    }

    public byte[] getResponseAsBytes() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Status line
            outputStream.write(("HTTP/1.1 " + status.getCode() + " " + status.getReasonPhrase() + "\r\n").getBytes());

            // Content-Type header (обязательно!)
            outputStream.write(("Content-Type: " + contentType + "\r\n").getBytes());


            // Connection: close (говорим клиенту закрыть соединение после ответа)
            outputStream.write("Connection: close\r\n".getBytes());

            // Custom headers
            if (headers != null) {
                outputStream.write(headers.toString().getBytes());
            }

            // Empty line between headers and body
            outputStream.write("\r\n".getBytes());

            // Body
            if (responseBody != null && responseBody.length > 0) {
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}