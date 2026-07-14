package com.junioroffer.infrastructure.offer.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;


public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse httpResponse) throws IOException {
        final HttpStatusCode statusCode = httpResponse.getStatusCode();

        if(statusCode.is5xxServerError()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while using http client");
        }

        if(statusCode.is4xxClientError()){
            if(statusCode == HttpStatus.NOT_FOUND){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            } else if (statusCode == HttpStatus.UNAUTHORIZED) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }

        }
    }

}
