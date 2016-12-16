

import java.io.IOException;

import com.pitchbook.extractor.PageExtractor;

/**
 * The Class MainApp which calls the PageExtracter
 */
public class MainApp {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException
	{
		PageExtractor extractor = new PageExtractor(args[0],args[1]);
		System.out.println(args[0]);
		extractor.extract();		
	}
	
}
