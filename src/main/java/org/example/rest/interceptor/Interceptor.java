package org.example.rest.interceptor;

import org.example.rest.context.RequestContext;
import org.example.rest.context.ResponseContext;

public interface Interceptor {
    void beforeSendResponse(RequestContext requestContext, ResponseContext responseContext);
}