package com.web.crawler.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web.crawler.utility.StringUtils;

@Service
public class SearchService {

	private final Logger logger = LoggerFactory.getLogger(SearchService.class);

	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;

	/**
	 * This performs all the work. It makes an HTTP request, checks the response,
	 * and then gathers up all the links on the page. Perform a searchForWord after
	 * the successful crawl
	 * 
	 * @param url - url to visit to find the html document for search.
	 * @return if the crawl gets successful or not.
	 */
	public boolean findPagesAndFormDocumentToSearch(String url) {

		try {
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			htmlDocument = connection.get();

			if (connection.response().statusCode() != 200) {
				logger.error("Webpage not found at " + url);
				return false;
			}
			if (!connection.response().contentType().contains("text/html")) {
				logger.error("Retrieved Webpage does not have HTML data " + url);
				return false;
			}

			Elements linksOnRetreivedDocument = htmlDocument.select("a[href]");
			logger.info("\n Found total (" + linksOnRetreivedDocument.size() + ") links on the retreived document on "
					+ url);

			if (linksOnRetreivedDocument.size() > 0) {
				links = linksOnRetreivedDocument.parallelStream().map(link -> link.absUrl("href"))
						.collect(Collectors.toList());
			}

			return true;

		} catch (IOException exc) {
			logger.error("HTTP request failed with error = " + exc);
			return false;
		}
	}

	/**
	 * Performs a words search on the body of the retrieved HTML document if the
	 * crawl is successful.
	 * 
	 * @param searchWord - The words to search.
	 * @return if the words search is successful or not.
	 */
	public String searchForWords(String[] searchWords, String url) {

		if (htmlDocument == null) {
			logger.error(
					"\n No HTML Document found on " + url + ", do crawling through the URLs before serach operation");
			return null;
		}
		if (searchWords == null) {
			logger.error("\n No search words provided for " + url);
			return null;
		}

		String bodyText = htmlDocument.body().text();

		if (StringUtils.isNotBlank(bodyText)) {
			String[] foundWordsArray = Arrays.stream(searchWords).filter(bodyText::contains).toArray(String[]::new);

			if (foundWordsArray != null && foundWordsArray.length > 0) {
				String foundWords = Arrays.asList(foundWordsArray).stream().collect(Collectors.joining(","));
				return foundWords;
			}
		}
		return null;
	}

	public List<String> getLinks() {
		return this.links;
	}
}
