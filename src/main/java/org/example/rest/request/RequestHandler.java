package org.example.rest.request;

import org.example.annotation.InjectByType;
import org.example.context.AppplicationContext;
import org.example.rest.context.RequestContext;
import org.example.rest.context.ResponseContext;
import org.example.rest.enums.HttpStatus;
import org.example.rest.exception.HandlerException;
import org.example.rest.handler.HandlerMethodResolver;
import org.example.rest.interceptor.InterceptorHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private final Socket clientSocket;

    private HandlerMethodResolver handlerMethodResolver;

    public RequestHandler(Socket socket) {
        this.clientSocket = socket;
        this.handlerMethodResolver = new HandlerMethodResolver();

    }

    @Override
    public void run() {
        try {
            var inputStream = clientSocket.getInputStream();
            var bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            var context = RequestContext.buildContext(bufferedReader);

            if (context == null) {
                System.out.println("Context is null!");
                return;
            }

            var os = clientSocket.getOutputStream();
            var handlerMethod = handlerMethodResolver.resolve(context);

            if (handlerMethod == null) {
                os.write(ResponseContext.build(HttpStatus.NOT_FOUND).getResponseAsBytes());
                os.flush();
            } else {
                ResponseContext responseContext = handlerMethod.invoke(context);
                if (responseContext.getStatus().isError()) {
                    os.write(ResponseContext.build(responseContext.getStatus()).getResponseAsBytes());
                    os.flush();
                } else {
                    InterceptorHolder.getInstance().beforeSendResponse(context, responseContext);
                    os.write(responseContext.getResponseAsBytes());
                    os.flush();
                }
            }
        } catch (IOException e) {
            throw new HandlerException("Handler exception", e);
        } finally {
            try {
                clientSocket.close();
                System.out.println("Socket closed");
            } catch (IOException e) {
                System.out.println("Exception trying to close socket");
            }
        }
    }
}