package org.example.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.annotation.InjectByType;
import org.example.annotation.betta.Driver;
import org.example.annotation.betta.PostgresDriver;
import org.example.database.pojo.Student;
import org.example.rest.bind.RequestMapping;
import org.example.rest.common.HttpHeaders;
import org.example.rest.context.RequestContext;
import org.example.rest.context.ResponseContext;
import org.example.rest.enums.HttpMethod;
import org.example.rest.enums.HttpStatus;
import org.example.rest.params.ApplicationParameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ApplicationController {

    @InjectByType
    private Driver database;

    @InjectByType
    private ObjectMapper objectMapper;

   /* public ApplicationController() {
        this.database = new PostgresDriver();
        this.objectMapper = new ObjectMapper();

    }*/


    @RequestMapping(path = "/students", method = HttpMethod.GET)
    public ResponseContext getStudents(RequestContext context) throws SQLException {
        List<Student> students = database.findStudent("SELECT * FROM student");
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(students);

            return ResponseContext.build(
                    HttpStatus.OK,
                    HttpHeaders.fromHeaderMap(Map.of("Content-Type", "text/plain",
                            "Content-Length", String.valueOf(bytes.length))),
                    bytes
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize students to JSON", e);
        }
    }


    @RequestMapping(path = "/", method = HttpMethod.GET)
    public ResponseContext simpleOk(RequestContext context) {
        return ResponseContext.build(HttpStatus.OK);
    }

    @RequestMapping(path = "/echo/{command}", method = HttpMethod.GET)
    public ResponseContext echo(RequestContext context) {
        var responseBody = context.getLastPart();

        return ResponseContext.build(
                HttpStatus.OK,
                HttpHeaders.fromHeaderMap(Map.of("Content-Type", "text/plain",
                        "Content-Length", String.valueOf(responseBody.getBytes().length))),
                responseBody
        );
    }

    @RequestMapping(path = "/user-agent", method = HttpMethod.GET)
    public ResponseContext userAgent(RequestContext context) {
        var responseBody = context.getHeaders().getFirst("User-Agent");

        return ResponseContext.build(
                HttpStatus.OK,
                HttpHeaders.fromHeaderMap(Map.of("Content-Type", "text/plain",
                        "Content-Length", String.valueOf(responseBody.getBytes().length))),
                responseBody
        );
    }

    @RequestMapping(path = "/files/{file}", method = HttpMethod.GET)
    public ResponseContext readFileByName(RequestContext context) {
        if (!ApplicationParameters.getInstance().isDirectoryExists()) {
            return ResponseContext.build(HttpStatus.NOT_FOUND);
        }

        var directory = ApplicationParameters.getInstance().getFileDirectory();
        directory = directory.endsWith("/") ? directory : directory + "/";
        var fileName = context.getLastPart();
        var file = new File(String.format("%s%s", directory, fileName));
        if (!file.exists() || !file.isFile()) {
            return ResponseContext.build(HttpStatus.NOT_FOUND);
        }

        var fileContent = readFile(file);

        return ResponseContext.build(
                HttpStatus.OK,
                HttpHeaders.fromHeaderMap(Map.of("Content-Type", "application/octet-stream",
                        "Content-Length", String.valueOf(fileContent.getBytes().length))),
                fileContent
        );
    }

    @RequestMapping(path = "/files/{file}", method = HttpMethod.POST)
    public ResponseContext saveFile(RequestContext context) {
        if (!ApplicationParameters.getInstance().isDirectoryExists()) {
            return ResponseContext.build(HttpStatus.NOT_FOUND);
        }

        var directory = ApplicationParameters.getInstance().getFileDirectory();
        directory = directory.endsWith("/") ? directory : directory + "/";
        var fileName = context.getLastPart();
        saveFile(String.format("%s%s", directory, fileName), context.getBody());

        return ResponseContext.build(HttpStatus.CREATED);
    }

    private String readFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            return new String(data);
        } catch (IOException ex) {
            throw new RuntimeException("Exception trying to read file", ex);
        }
    }

    private void saveFile(String fullPath, String content) {
        try {
            Path path = Paths.get(fullPath);

            if (Files.exists(path)) {
                Files.delete(path);
            }

            Files.write(path, content.getBytes());
        } catch (IOException ex) {
            throw new RuntimeException("Exception trying to save file", ex);
        }
    }
}