package com.web.crawler.service;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.crawler.controller.CrawlService;
import com.web.crawler.enums.APIAction;
import com.web.crawler.request.CrawlRequest;
import com.web.crawler.response.MessageResponse;
import com.web.crawler.response.Response;

@RestController
public class CrawlDemoController {

	private final Logger logger = LoggerFactory.getLogger(CrawlDemoController.class);

	@Inject
	private CrawlService crawlService;

	@PostMapping("/ingestUrl/searchText")
	public Response<?> ingestAndSearch(HttpServletRequest request, @RequestBody CrawlRequest crawlRequest) {

		try {
			if (crawlRequest == null || (crawlRequest.getIngestUrlsArray() == null)) {
				return Response.response(APIAction.FAILURE.action());
			}

			Map<String, String> urlWordsMap = crawlService.crawlIngestedUrls(crawlRequest.getIngestUrlsArray(),
					crawlRequest.getSearchTextsArray());

			return Response.response(APIAction.SUCCESS.action(), urlWordsMap);

		} catch (Exception e) {
			logger.error("Ingested URL List And Searched Words Api failed with error = " + e);

			MessageResponse messageResponse = new MessageResponse();
			messageResponse.setType("error");
			messageResponse.setDescription(e.getMessage());
			return Response.response(APIAction.FAILURE.action(), messageResponse);
		}
	}
}
