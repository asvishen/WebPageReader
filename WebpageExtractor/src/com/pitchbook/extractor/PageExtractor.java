package com.pitchbook.extractor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * The Class PageExtractor for extracting links, sequences and Tags
 * @Author Avijit Vishen
 */
public class PageExtractor {
	
	/** The url. */
	private String url;
	
	/** The output file. */
	private String outputFile;
	
	/** The links. */
	private List<String> links;
	
	/** The tags. */
	private String tags;
	
	/** The sequences. */
	private List<String> sequences;
	
	/** The page downloader. */
	private PageDownloader pageDownloader;
	
	/**
	 * Instantiates a new page extractor.
	 *
	 * @param url the url
	 * @param output the output file name
	 */
	public PageExtractor(String url, String output) {
		this.url = url;
		this.outputFile = output;
		links = new ArrayList<String>();
		sequences = new ArrayList<String>();
		pageDownloader = new PageDownloader(url);
			
	}
	
	/**
	 * Instantiates a new page extractor.
	 *
	 * @param url the url
	 * @param output the output file name
	 * @param pg the pg
	 */
	public PageExtractor(String url, String output,PageDownloader pg)
	{
		this.url = url;
		this.outputFile = output;
		links = new ArrayList<String>();
		sequences = new ArrayList<String>();
		pageDownloader = pg;
	}
	

	
	/**
	 * Extracts information from url and writes it to output file
	 *
	 * @throws IOException Error reading from url or writing to file
	 */
	public void extract() throws IOException
	{
		String html = pageDownloader.getRawPage();
		Document doc = Jsoup.parse(html);
		extractLinks(doc);
		extractTags(html);
		extractSequences(doc);
		writeToFile();

	}

	/**
	 * Write to file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void writeToFile() throws IOException {
		BufferedWriter bw = null;
		FileWriter fw = null;
		if(outputFile==null || outputFile.isEmpty()) outputFile = "output.txt";
		try {
			fw = new FileWriter(outputFile,true);
			bw = new BufferedWriter(fw);
			bw.write("[links]");
			bw.newLine();
			for(String line: links)
			{
				bw.write(line);
				bw.newLine();
			}
			bw.write("[HTML]");
			bw.newLine();
			bw.write(tags);
			bw.newLine();
			bw.write("[sequences]");
			bw.newLine();
			for(String line: sequences)
			{
				bw.write(line);
				bw.newLine();
			}
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
				if(fw != null)fw.close();
				if(bw != null)bw.close();

		}
		
	}

	/**
	 * Extract links.
	 *
	 * @param doc the doc
	 */
	private void extractLinks(Document doc) {
		Elements allLinks = doc.select("link");
		for(Element ele: allLinks )
		{
			links.add(ele.attr("href"));
		}
		Elements allAnchors = doc.select("a");
		for(Element ele: allAnchors )
		{
			links.add(ele.attr("href"));
		}
	}

	/**
	 * Extract tags.
	 *
	 * @param all the all
	 */
	private void extractTags(String all) {
		StringBuilder bd = new StringBuilder();
		
		while(true){
			
				int start = all.indexOf("<");
				int endBracket = all.indexOf(">",start);
				if(endBracket==-1) break;
				int endSpace = all.indexOf(" ",start);
				if(endSpace < endBracket && endSpace > -1)
				{
					bd.append(all.substring(all.indexOf("<"),endSpace));
				}
				else if(endBracket > -1)
				{
					bd.append(all.substring(all.indexOf("<"),endBracket));
				}
				if(all.charAt(endBracket-1) == '/') bd.append("/");
				bd.append(">");
				all = all.substring(endBracket+1);
				if(all.indexOf("<") == -1) break;
				
			}
		tags=bd.toString();

	}
	
	/**
	 * Extract sequences.
	 *
	 * @param doc the doc
	 */
	private void extractSequences(Document doc) {
		StringBuilder bd;
		for (Element el:doc.select("*")) 
		{
			String text =el.ownText();
			if(text.isEmpty()) continue;
			String[] words = text.split(" ");
		    bd = new StringBuilder();
		    int count = 0;
			for(int i=0;i<words.length;i++)
			{
				if(words[i].matches("[A-Z][A-Za-z]+"))
				{
					bd.append(words[i]).append(" ");
					count++;
				}
				else if(words[i].matches("[A-Z][A-Za-z]+,") && count>0)
				{
					bd.append(words[i].substring(0, words[i].length()-1));
					sequences.add(bd.toString().trim());
					bd.setLength(0);
					count=0;
				}
				else if(count>1)
					
					{
						sequences.add(bd.toString().trim());
						count =0;
						bd.setLength(0);
					}
				else {bd.setLength(0);count=0;}
			}
			if(bd.length()!=0 && count>1) 	sequences.add(bd.toString().trim());
		}
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public String getOutpuFilet() {
		return outputFile;
	}

	/**
	 * Sets the output.
	 *
	 * @param output the new output
	 */
	public void setOutputFile(String output) {
		this.outputFile = output;
	}

	/**
	 * Gets the links.
	 *
	 * @return the links
	 */
	public List<String> getLinks() {
		return links;
	}

	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * Gets the sequences.
	 *
	 * @return the sequences
	 */
	public List<String> getSequences() {
		return sequences;
	}


	
}
