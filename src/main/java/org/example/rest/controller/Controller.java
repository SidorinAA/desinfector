package org.example.rest.controller;

import org.example.rest.context.RequestContext;
import org.example.rest.context.ResponseContext;

import java.sql.SQLException;

public interface Controller {

    ResponseContext getStudents(RequestContext context) throws SQLException;
}
