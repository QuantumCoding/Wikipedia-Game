package window.browser;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class WikipediaURLStreamHandlerFactory implements URLStreamHandlerFactory {

	public WikipediaURLStreamHandlerFactory() {}

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandlerFactory#createURLStreamHandler(java.lang.String)
	 */
	@Override
	public URLStreamHandler createURLStreamHandler(String protocol) {
		if(protocol.equals("wikipedia"))
			return new WikipediaURLStreamHandler();
		return null;
	}

}
