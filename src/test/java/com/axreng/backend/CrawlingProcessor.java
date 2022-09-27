package com.axreng.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.axreng.backend.crawling.Crawling;
import com.axreng.backend.crawling.Utils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CrawlingProcessor {

	@Mock
	private Utils ioUtils;

	@InjectMocks
	private Crawling crawlingProcessor;

	public void setUp(String url, String keyword, String maxResults) throws IOException {
		this.crawlingProcessor = new Crawling(url, keyword, maxResults);
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void shouldVisitTwoPagesFindTwoResults01() throws IOException {
		setUp("http://hiring.axreng.com/", "before", "-1");
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/"))).willReturn(getLines(1));
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/index8.html"))).willReturn(getLines(2));

		this.crawlingProcessor.executeCrawling();

		assertEquals(2, this.crawlingProcessor.getResultSet().size());
	}

	/*
	 * Test case insensitive.
	 */
	@Test
	public void shouldVisitTwoPagesFindTwoResults02() throws IOException {
		setUp("http://hiring.axreng.com/", "BeFoRe", "-1");
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/"))).willReturn(getLines(1));
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/index8.html"))).willReturn(getLines(2));

		this.crawlingProcessor.executeCrawling();

		assertEquals(2, this.crawlingProcessor.getResultSet().size());
	}

	@Test
	public void shouldVisitTwoPagesFindOneResult() throws IOException {
		setUp("http://hiring.axreng.com/", "before", "1");
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/"))).willReturn(getLines(1));
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/index8.html"))).willReturn(getLines(2));

		this.crawlingProcessor.executeCrawling();

		assertEquals(1, this.crawlingProcessor.getResultSet().size());
	}

	@Test
	public void shouldNotVisitSecondPage() throws IOException {
		setUp("http://hiring.axreng.com/", "before", "1");
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/"))).willReturn(getLines(1));
		given(ioUtils.getHtmlLines(new URL("http://hiring.axreng.com/index8.html"))).willReturn(getLines(3));

		this.crawlingProcessor.executeCrawling();

		assertEquals(1, this.crawlingProcessor.getResultSet().size());
	}

	private List<String> getLines(Integer lineSelect) {
		switch (lineSelect) {
			case 0:
				return Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", //
						"<span class=\"country\">USA</span><br /> href=\"index.html\"",
						"</p></div></div><div class=\"navfooter\"><hr /><table width=\"100%\" summary=\"Navigation footer\"> ");//
			case 1:
				return Arrays.asList(
						"<a class=\"ulink\" target=\"_top\"><span class=\"package\">OpenLDAP</span> 2.4.48</a></li><li class=\"listitem\">", //
						"	You should confirm the source of any errors in this documentation, before",
						"href=\"index8.html\"");//
			case 2:
				return Arrays.asList("http://hiring.axreng.com/index.html before");
			case 3:
				return Arrays.asList("http://hiring.axreng");
			default:
				return new ArrayList<>();

		}

	}

}