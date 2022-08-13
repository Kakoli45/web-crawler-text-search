package com.web.crawler.controller;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.crawler.enums.APIAction;
import com.web.crawler.response.MessageResponse;
import com.web.crawler.response.Response;
import com.web.crawler.service.CrawlService;

@RestController
public class CrawlDemoController {

	private final Logger logger = LoggerFactory.getLogger(CrawlDemoController.class);

	@Inject
	private CrawlService crawlService;

	@GetMapping(path = "/ingestUrl/searchText", consumes = "application/x-www-form-urlencoded")
	public Response<?> ingestAndSearch(HttpServletRequest request,
			@RequestParam(value = "ingestedUrlsArray", required = true) String[] ingestedUrlsArray,
			@RequestParam(value = "searchTextsArray", required = true) String[] searchTextsArray) {

		try {
			Map<String, String> urlWordsMap = crawlService.crawlIngestedUrls(ingestedUrlsArray, searchTextsArray);

			return Response.response(APIAction.SUCCESS.action(), urlWordsMap);

		} catch (Exception exc) {
			logger.error("Ingested URL List And Searched Words Api failed with error = " + exc);

			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setType("error");
			messageResponse.setDescription(exc.getMessage());
			return Response.response(APIAction.FAILURE.action(), messageResponse);
		}
	}
}
