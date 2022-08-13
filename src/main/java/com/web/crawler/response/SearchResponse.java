package com.web.crawler.response;

import java.io.Serializable;
import java.util.Map;

import com.web.crawler.utility.JSONMapper;

public class SearchResponse implements Serializable {

	/**
	 * serial id for serialzation-deserialization
	 */
	private static final long serialVersionUID = 1L;

	private String[] searchTextArray;

	Map<String, String[]> foundWordsPerUrl;

	public String[] getSearchTextArray() {
		return searchTextArray;
	}

	public void setSearchTextArray(String[] searchTextArray) {
		this.searchTextArray = searchTextArray;
	}

	public Map<String, String[]> getFoundWordsPerUrl() {
		return foundWordsPerUrl;
	}

	public void setFoundWordsPerUrl(Map<String, String[]> foundWordsPerUrl) {
		this.foundWordsPerUrl = foundWordsPerUrl;
	}

	public String toString() {
		return JSONMapper.toJSON(this);
	}
}