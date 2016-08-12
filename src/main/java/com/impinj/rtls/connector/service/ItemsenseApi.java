package com.impinj.rtls.connector.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.impinj.rtls.connector.app.config.ConnectorConfiguration;
import com.impinj.rtls.connector.app.config.ItemsenseConfig;
import com.impinj.auth.ImpinjApiException;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ralemy on 8/9/16.
 * The responsibility of this object is to abstract Itemsense Restful calls.
 */
public class ItemsenseApi {
    public static String REGISTER_QUEUE = "/itemsense/data/v1/messageQueues/zoneTransition/configure";
    private final ItemsenseConfig config;
    private BasicCredentialsProvider credentialsProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final Logger log = LoggerFactory.getLogger(ItemsenseApi.class);
    @Inject
    public ItemsenseApi(ConnectorConfiguration config){
        this.config = config.getItemsenseConfig();
        this.credentialsProvider = new BasicCredentialsProvider();
        this.credentialsProvider.setCredentials(
                new AuthScope(this.config.getBaseUrl(), this.config.getBasePort(), AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(this.config.getUsername(), this.config.getPassword())
        );
        log.info("------------> user: {}, pass {} ", this.config.getUsername(), this.config.getPassword());
    }

    public <T> T doGet(String url, Class<T> cls) throws IOException, ImpinjApiException {
        try(CloseableHttpClient httpClient = makeAuthenticatedClient()){
            HttpGet get = new HttpGet(cleanUrl(url));
            try (CloseableHttpResponse response = httpClient.execute(get)) {
                return processResponse(response,cls);
            }
        }
    }

    public <T> T doPost(String url, HttpEntity payload, Class<T> cls) throws IOException, ImpinjApiException {
        try(CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultCredentialsProvider(this.credentialsProvider).build()){
            HttpPost post = new HttpPost(cleanUrl(url));
            post.addHeader("Content-Type","application/json");
            post.setEntity(payload);
            log.info("------------> posting {} {} {}", post.getMethod(),post.getRequestLine(), post.getURI().toString());
            try(CloseableHttpResponse response = httpClient.execute(post)){
                return processResponse(response,cls);
            }
        }
    }

    private String cleanUrl(String url){
        return "http://" + (this.config.getBaseUrl().replaceAll("^http.//","").replaceAll("/$","")) + "/" + url.replaceAll("^/","").replaceAll("/$","") ;
    }
    private <T> T processResponse(CloseableHttpResponse response, Class<T> cls) throws IOException, ImpinjApiException {
        if(response.getStatusLine().getStatusCode() > 399) {
            log.info("Status: {}, Message: {}",response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity()));
            throw new ImpinjApiException(response);
        }
        return objectMapper.readValue(response.getEntity().getContent(),cls);
    }

    private CloseableHttpClient makeAuthenticatedClient(){
        return HttpClients.custom().setDefaultCredentialsProvider(this.credentialsProvider).build();
    }

    public class dateSerializer extends JsonSerializer<Date> {

        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            jsonGenerator.writeString(formatter.format(date) + "Z[Etc/UTC]");
        }
    }

}
