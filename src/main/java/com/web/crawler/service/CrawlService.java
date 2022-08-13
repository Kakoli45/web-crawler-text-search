package com.web.crawler.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.web.crawler.utility.StringUtils;

@Service
public class CrawlService {

	private final Logger logger = LoggerFactory.getLogger(CrawlService.class);

	private static final int MAX_PAGES_TO_SEARCH = 10;
	private Set<String> pagesVisited = new HashSet<String>();
	private List<String> pagesToVisit = new LinkedList<String>();
	private Map<String, String> urlWordsMap = new HashMap<String, String>();

	@Inject
	private SearchService searchService;

	/**
	 * Crawl the webpages as per the URLs making HTTP request and parse the response
	 * (the web page and create the HTML document to search the required word
	 * 
	 * @param url        - The starting point of the spider
	 * @param searchWord - The word or string that you are searching for
	 */
	public Map<String, String> crawlIngestedUrls(String[] ingestedUrlsArray, String[] searchWords) {

		String[] ingestedUrls = parseUrls(ingestedUrlsArray);

		if (ingestedUrls == null) {
			return urlWordsMap;
		}

		Arrays.stream(ingestedUrls).forEach(url -> {
			int length = this.pagesVisited.size();

			IntStream.rangeClosed(length, MAX_PAGES_TO_SEARCH - 1).forEach(i -> {
				try {
					findPagesAndSearchTexts(url, searchWords);
					this.pagesToVisit.addAll(searchService.getLinks());

				} catch (Exception exc) {
					logger.error("Find pages and search text failed with error = " + exc);
				}
			});
		});

		return urlWordsMap;
	}

	private void findPagesAndSearchTexts(String url, String[] searchWords) throws Exception {

		String currentUrl = "";

		if (this.pagesToVisit.isEmpty()) {
			currentUrl = url;
			this.pagesVisited.add(url);
		} else {
			currentUrl = this.nextUrl();
		}

		boolean documentFound = searchService.findPagesAndFormDocumentToSearch(currentUrl);

		if (documentFound) {
			String foundWords = searchService.searchForWords(searchWords, currentUrl);

			if (StringUtils.isNotBlank(foundWords)) {
				logger.info("\n Success : Words = \"" + foundWords + "\" found at " + currentUrl);
				urlWordsMap.put(currentUrl, foundWords);
			}
		}
	}

	/**
	 * Returns the parsedURLS which are received from request param of crawl search
	 * api
	 * 
	 * @return parsedUrls String[]
	 */
	private String[] parseUrls(String[] ingestedUrlsArray) {

		String ingestUrlString = Arrays.asList(ingestedUrlsArray).stream().collect(Collectors.joining(","));

		try {
			URI ingestUris = new URI(ingestUrlString);
			String urls = new URI(ingestUris.getScheme(), ingestUris.getAuthority(), ingestUris.getPath(), null,
					ingestUris.getFragment()).toString();

			if (StringUtils.isNotBlank(urls)) {
				String[] ingestedUrls = urls.trim().split(",");
				return ingestedUrls;
			}

		} catch (URISyntaxException exc) {
			logger.error("HTTP request URLS parsing failed with error = " + exc);
		}

		return null;
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We also do
	 * a check to make sure this method doesn't return a URL that has already been
	 * visited.
	 * 
	 * @return nextUrl String
	 */
	private String nextUrl() {
		String nextUrl = this.pagesToVisit.remove(0);
		String confg = pagesVisited.stream().filter(r -> pagesVisited.contains(nextUrl)).findAny()
				.orElse(this.pagesToVisit.remove(0));
		this.pagesVisited.add(nextUrl);
		return confg;
	}
}
