package com.web.crawler.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	public Map<String, String> crawlIngestedUrls(String[] ingestedUrls, String[] searchWords) {

		Arrays.stream(ingestedUrls).forEach(url -> {
			int length = this.pagesVisited.size();

			IntStream.rangeClosed(length, MAX_PAGES_TO_SEARCH - 1).forEach(i -> {
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

					if (foundWords != null && !foundWords.isEmpty()) {
						logger.info("\n Success : Words = \"" + foundWords + "\" found at " + currentUrl);
						urlWordsMap.put(currentUrl, foundWords);
					}
				}
				this.pagesToVisit.addAll(searchService.getLinks());
			});
		});
		return urlWordsMap;
	}

	/**
	 * Returns the next URL to visit (in the order that they were found). We also do
	 * a check to make sure this method doesn't return a URL that has already been
	 * visited.
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextUrl = this.pagesToVisit.remove(0);
		String confg = pagesVisited.stream().filter(r -> pagesVisited.contains(nextUrl)).findAny()
				.orElse(this.pagesToVisit.remove(0));
		return confg;
	}

	public static void main(String[] args) {
		CrawlService spider = new CrawlService();
		String[] urls = { ("http://arstechnica.com/"), "" };
		String[] words = { "computer", "com" };
		spider.crawlIngestedUrls(urls, words);
	}
}
