package com.pitchbook.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class PageDowloaderTest.
 */
public class PageDowloaderTest {

	/**
	 * Test invalid protocol link.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testInvalidProtocolLink() throws IOException
	{
		PageDownloader pd = new PageDownloader("ftp://google.com");
		try{
			pd.getRawPage();
			fail();
		}
		catch(IllegalArgumentException ex)
		{
			assertEquals("Protocol not Supported. Try with HTTP or HTTPS",ex.getMessage());
		}
	}
	
	/**
	 * Test no link.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testNoLink() throws IOException
	{
		PageDownloader pd = new PageDownloader("");
		try{
			pd.getRawPage();
			fail();
		}
		
		catch(IllegalArgumentException ex)
		{
			assertEquals("Link Not Provided",ex.getMessage());
		}
	}
	
	/**
	 * Test valid protocol link without http.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testValidProtocolLinkWithoutHttp() throws IOException
	{
		PageDownloader pd = new PageDownloader("pitchbook.com");

		assertTrue(!pd.getRawPage().isEmpty());

	}
	
	/**
	 * Test valid protocol link with http.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testValidProtocolLinkWithHttp() throws IOException
	{
		PageDownloader pd = new PageDownloader("https://pitchbook.com");

		assertTrue(!pd.getRawPage().isEmpty());

	}
	
}
