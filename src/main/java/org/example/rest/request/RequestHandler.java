package org.example.rest.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotation.InjectByType;
import org.example.annotation.betta.Driver;
import org.example.context.AppplicationContext;
import org.example.database.pojo.Student;
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
import java.sql.SQLException;
import java.util.List;

public class RequestHandler implements Runnable {

    private final Socket clientSocket;

    private HandlerMethodResolver handlerMethodResolver;
    private AppplicationContext appplicationContext;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RequestHandler(Socket socket, AppplicationContext context) {
        this.clientSocket = socket;
        this.handlerMethodResolver = new HandlerMethodResolver();
        this.appplicationContext = context;

    }

    @Override
    public void run() {
        try {

            var inputStream = clientSocket.getInputStream();
            var bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //bufferedReader
            var context = RequestContext.buildContext(bufferedReader); //read requestContext

            if (context == null) {
                System.out.println("Context is null!");
                return;
            }

            var os = clientSocket.getOutputStream(); //outputStream

            var handlerMethod = handlerMethodResolver.resolve(context); //handlerMethod (instance of controller, method, path, method)

            if (handlerMethod == null) {
                os.write(ResponseContext.build(HttpStatus.NOT_FOUND).getResponseAsBytes());
                os.flush();
            } else {

                ResponseContext responseContext = handlerMethod.invoke(context); //responseContext
                Driver driver = appplicationContext.getObject(Driver.class);
                System.out.println("DRIVER:  " + driver);
                List<Student> students = driver.findStudent("select * from student");
                System.out.println("HERE STUDENTS: ");
                students.forEach(System.out::println);
                byte[] bytes = objectMapper.writeValueAsBytes(students);
                responseContext.setResponseBody(bytes);
                responseContext.setContentType("application/json"); // ВАЖНО!

                if (responseContext.getStatus().isError()) {
                    os.write(ResponseContext.build(responseContext.getStatus()).getResponseAsBytes());
                    os.flush();
                } else {
                    //InterceptorHolder.getInstance().beforeSendResponse(context, responseContext); // сжатие
                    os.write(responseContext.getResponseAsBytes()); //write getResponseAsBytes
                    os.flush();
                }
            }
        } catch (IOException e) {
            throw new HandlerException("Handler exception", e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
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