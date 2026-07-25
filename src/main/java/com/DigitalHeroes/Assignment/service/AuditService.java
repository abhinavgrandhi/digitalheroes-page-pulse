package com.DigitalHeroes.Assignment.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import com.DigitalHeroes.Assignment.dto.AuditResponse;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.io.IOException;
import java.net.http.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class AuditService {
	private String getMetaDescription(Document document) {

	    return document.select("meta[name=description]")
	            .attr("content");
	}
	private int getH1Count(Document document) {

	    return document.select("h1").size();
	}
	private int getImagesWithoutAlt(Document document) {

	    Elements images = document.select("img");

	    int missingAlt = 0;

	    for (Element image : images) {

	        if (!image.hasAttr("alt")
	                || image.attr("alt").trim().isEmpty()) {

	            missingAlt++;
	        }
	    }

	    return missingAlt;
	}
	private int getWordCount(Document document) {

	    String text = document.body().text();

	    if (text.trim().isEmpty()) {
	        return 0;
	    }

	    return text.trim().split("\\s+").length;
	}

	
	public AuditResponse audit(String url) {

	    validateUrl(url);

	    try {

	        HttpClient client = HttpClient.newBuilder()
	                .connectTimeout(Duration.ofSeconds(5))
	                .followRedirects(HttpClient.Redirect.NORMAL)
	                .build();

	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(URI.create(url))
	                .GET()
	                .build();

	        long startTime = System.currentTimeMillis();

	        HttpResponse<String> response = client.send(
	                request,
	                HttpResponse.BodyHandlers.ofString());

	        long endTime = System.currentTimeMillis();

	        // Check whether the response is HTML
	        String contentType = response.headers()
	                .firstValue("Content-Type")
	                .orElse("");

	        if (!contentType.toLowerCase().contains("text/html")) {
	            throw new IllegalArgumentException("URL does not return HTML content.");
	        }

	        Document document = Jsoup.parse(response.body());

	        AuditResponse auditResponse = new AuditResponse();

	        auditResponse.setStatus(response.statusCode());
	        auditResponse.setResponseTime(endTime - startTime);
	        auditResponse.setTitle(document.title());
	        auditResponse.setMetaDescription(getMetaDescription(document));
	        auditResponse.setH1Count(getH1Count(document));
	        auditResponse.setImagesWithoutAlt(getImagesWithoutAlt(document));
	        auditResponse.setWordCount(getWordCount(document));

	        return auditResponse;

	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException("Request was interrupted.");
	    } catch (IOException e) {
	        throw new RuntimeException("Unable to fetch webpage.");
	    }
	}
    private void validateUrl(String url) {

        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be empty.");
        }

        try {

            URI uri = new URI(url);

            if (uri.getScheme() == null || uri.getHost() == null) {
                throw new IllegalArgumentException("Invalid URL.");
            }

            if (!uri.getScheme().equalsIgnoreCase("http")
                    && !uri.getScheme().equalsIgnoreCase("https")) {

                throw new IllegalArgumentException("Only HTTP and HTTPS URLs are allowed.");
            }

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL.");
        }
    }
}