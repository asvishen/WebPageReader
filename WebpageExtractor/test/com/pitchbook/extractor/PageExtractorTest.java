package com.pitchbook.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;


/**
 * The Class PageExtractorTest.
 */
public class PageExtractorTest {

	/**
	 * Test tags.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testTags() throws IOException
	{
		FileReader fr = null;
		try {
			File file = new File("sample.html");
			fr = new FileReader(file);
			char [] a = new char[1000];
			fr.read(a);
			PageDownloader pe = Mockito.mock(PageDownloader.class);
			Mockito.when(pe.getRawPage()).thenReturn(String.valueOf(a));
			String expected = "<html><head><title></title><meta><meta/><meta/><script></script><link/><link/>"
					+ "</head><body><div><div><div><div><span></span><p></p></div></body></html>";
			PageExtractor actual = new PageExtractor("","testout.txt",pe);
			actual.extract();
			assertEquals(expected,actual.getTags());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(fr!=null) fr.close();
		}
	}
	
	/**
	 * Test sequences extraction.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testSequencesExtraction() throws IOException
	{
		FileReader fr = null;
		try {
			File file = new File("sampleForSequences.html");
			fr = new FileReader(file);
			char [] a = new char[1000];
			fr.read(a);
			List<String> expected = new ArrayList();
			expected.add("John Gabbert");
			expected.add("Rod Diefendorf");
			expected.add("Chief Operating Officer");
			expected.add("Fabrice Forget");
			expected.add("Chief Product Officer");
			expected.add("The VP");
			expected.add("Market Development");
			expected.add("Adley Bowden");
			PageDownloader pe = Mockito.mock(PageDownloader.class);
			Mockito.when(pe.getRawPage()).thenReturn(String.valueOf(a));
			PageExtractor actual = new PageExtractor("","testout.txt",pe);
			actual.extract();
			assertEquals(expected,actual.getSequences());
			assertEquals(8,actual.getSequences().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			fr.close();
		}
		

	}
	
	/**
	 * Test links.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testLinks() throws IOException
	{
		FileReader fr=null;
		try {
			File file = new File("sample.html");
			fr = new FileReader(file);
			char [] a = new char[1000];
			fr.read(a);
			PageDownloader pe = Mockito.mock(PageDownloader.class);
			Mockito.when(pe.getRawPage()).thenReturn(String.valueOf(a));
			String expected = "<html><head><title></title><meta><meta/><meta/><script></script><link/><link/>"
					+ "</head><body><div><div><div><div><span></span><p></p></div></body></html>";
			PageExtractor actual = new PageExtractor("","testout.txt",pe);
			actual.extract();
			assertEquals(expected,actual.getTags());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			fr.close();
		}
	}
	
	/**
	 * Test link null.
	 */
	@Test
	public void testLinkNull()
	{
		PageExtractor ex = new PageExtractor("", "");
		try{
			ex.extract();
			fail();
		}
		catch(IllegalArgumentException e)
		{
			assertEquals("Link Not Provided",e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test invalid HTML page.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void testInvalidHTMLPage() throws IOException
	{
		FileReader fr =null;
		try {
			File file = new File("empty.html");
			fr = new FileReader(file);
			char [] a = new char[1000];
			fr.read(a);
			PageDownloader pe = Mockito.mock(PageDownloader.class);
			Mockito.when(pe.getRawPage()).thenReturn(String.valueOf(a));
			PageExtractor actual = new PageExtractor("","testout.txt",pe);
			actual.extract();
			assertEquals("",actual.getTags());
			assertEquals(0,actual.getLinks().size());
			assertEquals(0,actual.getSequences().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			fr.close();
		}
		
	}
}
