package org.example.rest.server;

import org.example.annotation.InjectByType;
import org.example.annotation.Singleton;
import org.example.context.AppplicationContext;
import org.example.rest.request.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class Server {

    private final int port;

    private final ExecutorService executorService;

    private AppplicationContext appplicationContext;

    public Server(int port,  AppplicationContext appplicationContext) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
        this.appplicationContext = appplicationContext;
    }

    public void start() {
        try (var serverSocket = new ServerSocket(port)) {
            serverSocket.setReuseAddress(true);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("accepted new connection");
                executorService.execute(new RequestHandler(clientSocket, appplicationContext));
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
