package com.axreng.backend;


import com.axreng.backend.crawling.Crawling;
import com.axreng.backend.crawling.Utils;
import com.axreng.backend.exception.InputException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Matheus Longo
 *
 */

public class CrawlingProcessorInputTest {

	private static Utils ioUtils;

	@BeforeAll
	public static void setUp() {
		ioUtils = new Utils();
	}

	/*
	 * Should fail: URL not present.
	 */
	@Test
	public void urlNotPresentTest() {
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL(null));
	}

	/*
	 * Should fail: Keyword not present.
	 */
	@Test
	public void keyWordNotPresentTest() {
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword(null));
	}

	/*
	 * Should fail: No protocol in the URL.
	 */
	@Test
	public void noProtocolTest() {
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL("hiring.axreng.com/"));
	}

	/*
	 * Should fail: Invalid URL.
	 */
	@Test
	public void invalidUrlTest() {
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL("http://hiri%ng.axreng.com/"));
	}

	/*
	 * Should fail: Invalid Keyword due length < 4 or length > 32.
	 */
	@Test
	public void invalidKeywordSizeTest01() {
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fou"));
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fourfourfourfourfourfourfourfourf"));
	}

	/*
	 * Should fail: Invalid Keyword due non alphanumeric character.
	 */
	@Test
	public void invalidKeywordNotAlphaNumeric() {
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fou&"));
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fo.ur"));
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fo ur"));
	}

	/*
	 * Should Pass with no max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor01() throws InputException {
		Crawling cp = new Crawling("http://hiring.axreng.com/", "fou4", "-1");

		assertEquals(-1, cp.getMaxResults());
	}
}