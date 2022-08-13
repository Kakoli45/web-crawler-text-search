package com.web.crawler.request;

import java.io.Serializable;

import com.web.crawler.utility.JSONMapper;

public class CrawlRequest implements Serializable {

	/**
	 * serial id for serialzation-deserialization
	 */
	private static final long serialVersionUID = 1L;

	private String[] searchTextsArray;

	private String[] ingestUrlsArray;

	public String[] getSearchTextsArray() {
		return searchTextsArray;
	}

	public void setSearchTextsArray(String[] searchTextsArray) {
		this.searchTextsArray = searchTextsArray;
	}

	public String[] getIngestUrlsArray() {
		return ingestUrlsArray;
	}

	public void setIngestUrlsArray(String[] ingestUrlsArray) {
		this.ingestUrlsArray = ingestUrlsArray;
	}

	public String toString() {
		return JSONMapper.toJSON(this);
	}
}