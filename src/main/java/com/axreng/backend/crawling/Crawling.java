package com.axreng.backend.crawling;

import com.axreng.backend.exception.InputException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Matheus Longo
 *
 */

public class Crawling extends Utils {

	private final String HREF_REGEX = "href=\"(.*?)\"";
	int count = 0 ;
	private URL baseUrl;
	private String keyword;
	private Integer maxResults;
	private Set<String> visitedUrls;
	private Set<String> foundUrls;

	private Set<String> resultSet;

	private Utils ioUtils;

	public Crawling(String baseUrl, String keyword, String maxResults) throws InputException {

		this.ioUtils= new Utils();

		this.baseUrl = this.ioUtils.verifyBaseURL(baseUrl);
		this.keyword = this.ioUtils.verifyKeyword(keyword);
		this.maxResults = this.ioUtils.verifyMaxResults(maxResults);

		this.visitedUrls = new HashSet<>();
		this.foundUrls = new HashSet<>();
		this.resultSet = new HashSet<>();
	}

	public void executeCrawling() throws IOException {
		processUrl(this.baseUrl);
		this.ioUtils.printResults(this.baseUrl.toString(), this.keyword, this.resultSet);

	}

	private void processUrl(URL currentUrl ) throws IOException{
		System.out.println("Processando URL: " + currentUrl);
		System.out.println("TOTAL DE URI's ACESSADAS: " + count++);
		if(this.maxResults != -1 && resultSet.size() >= this.maxResults) {
			return;
		}

		List<String> lines = this.ioUtils.getHtmlLines(currentUrl);
		if(findKeyword(lines, keyword)) {
			this.resultSet.add(currentUrl.toString());
		}

		Map<String, URL> internalUrls = findInternalUrls(lines, currentUrl.toString());
		for(URL internalUrl: internalUrls.values()) {
			processUrl(internalUrl);
		}
		visitedUrls.add(currentUrl.toString());
	}

	private Boolean findKeyword(List<String> lines, String keyword) {
		Pattern p = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		return lines.stream().anyMatch(line -> p.matcher(line).find());
	}

	private Map<String, URL> findInternalUrls(List<String> lines, String actualUrl) throws MalformedURLException{
		Map<String, URL> internalUrls = new HashMap<>();
		Pattern p = Pattern.compile(HREF_REGEX, Pattern.DOTALL);
		for(String line: lines) {
			Matcher m = p.matcher(line);

			while(m.find()) {
				String urlStr = m.group().split("\"")[1];
				if(!urlStr.endsWith(".html") || urlStr.startsWith("mailto")) {
					continue;
				}

				URI uri = URI.create(urlStr);
				URL url = contructUrl(uri, actualUrl);
				if(url == null) {
					continue;
				}

				urlStr = url.toString();
				if(!this.foundUrls.contains(urlStr) && !this.visitedUrls.contains(urlStr) && !internalUrls.containsKey(urlStr)) {
					internalUrls.put(urlStr, url);
					this.foundUrls.add(urlStr);
				}
			}
		}
		return internalUrls;
	}

	private URL contructUrl(URI uri, String currentUrl) throws MalformedURLException {
		String uriStr = uri.toString();
		if(uri.isAbsolute()) {
			if(uriStr.contains(currentUrl)) {
				return new URL(uri.toString());
			}
		} else if(uriStr.startsWith("../")) {
			return new URL(this.baseUrl+uri.toString().replace("../", ""));
		} else {
			return new URL(this.baseUrl+uri.toString());
		}
		return null;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public Set<String> getResultSet(){
		return this.resultSet;
	}
}
