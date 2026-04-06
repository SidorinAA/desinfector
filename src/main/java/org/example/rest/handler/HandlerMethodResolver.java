package org.example.rest.handler;

import org.example.rest.bind.HandlerHolder;
import org.example.rest.bind.HandlerMethod;
import org.example.rest.context.RequestContext;
import org.example.rest.util.PathPattern;

public class HandlerMethodResolver {

    public HandlerMethod resolve(RequestContext context) {
        return HandlerHolder.getInstance().getHandlerMethods()
                .stream()
                .filter(it -> context.getMethod() == it.getMethod() &&
                        PathPattern.path(it.getPath()).match(context.getPath()))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Handler by context not found: " + context);
                    return null;
                });
    }
}
