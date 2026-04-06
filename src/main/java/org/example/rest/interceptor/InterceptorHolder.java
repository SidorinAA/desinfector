package org.example.rest.interceptor;

import org.example.rest.context.RequestContext;
import org.example.rest.context.ResponseContext;

import java.util.ArrayList;
import java.util.List;

public class InterceptorHolder {
    private static volatile InterceptorHolder INSTANCE;

    private final List<Interceptor> interceptors;

    private InterceptorHolder() {
        this.interceptors = new ArrayList<>();
        interceptors.add(new EncodeInterceptor());
    }

    public static InterceptorHolder getInstance() {
        if (INSTANCE == null) {
            synchronized (InterceptorHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new InterceptorHolder();
                }
            }
        }
        return INSTANCE;
    }

    public void beforeSendResponse(RequestContext requestContext, ResponseContext responseContext) {
        interceptors.forEach(it -> it.beforeSendResponse(requestContext, responseContext));
    }
}