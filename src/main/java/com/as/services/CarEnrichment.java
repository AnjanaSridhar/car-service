package com.as.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CarEnrichment {

    private final String url;

    @Autowired
    public CarEnrichment(@Value("${api.base.url}") String url) {
        this.url = url;
    }

    public String soundsSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON(this.url + "?sl=" + s);
    }

    @SneakyThrows
    private String getJSON(String datamuseUrl) {
        URL url;
        URLConnection dc;
        StringBuilder s = null;
        BufferedReader in;
        try {
            url = new URL(datamuseUrl);
            dc = url.openConnection();
            in = new BufferedReader(new InputStreamReader(dc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            s = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                s.append(inputLine);
            in.close();
        } catch (IOException e) {
            log.error("Exception during calling API ", e);
        }
        return s != null ? String.valueOf(s) : null;
    }
}
