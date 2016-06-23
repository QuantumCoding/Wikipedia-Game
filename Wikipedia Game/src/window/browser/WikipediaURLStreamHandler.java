package window.browser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class WikipediaURLStreamHandler extends URLStreamHandler {
	private static final String HOST  = ".org";
	private static final String REF   = "#";
	private static final String QUERY = "?";
	
	
	public WikipediaURLStreamHandler() {}

	protected URLConnection openConnection(URL u) throws IOException {
		return new WikipediaURLConnection(u);
	}
	
	public void parseURL(URL url, String spec, int start, int limit) {
//		String rawIn = spec;
		
		String protocol = url.getProtocol();
	    String authority = null;// url.getAuthority();
	    String userInfo = null;// url.getUserInfo();
	    String host = "";// url.getHost();
	    int port = -1;// url.getPort();
	    String path = null;// url.getPath();
	    String query = null;// url.getQuery();
	    String ref = null;// url.getRef();
	    
//	    while(spec.charAt(++ start) == '/');
//	    spec = spec.substring(start, limit);
//	    System.out.println("Spec: " + spec);
	    
	    int index = spec.indexOf("://");
	    if(index != -1) {
	    	protocol = spec.substring(0, index);
	    	spec = spec.substring(index + 3);
	    }
	    
	    index = spec.indexOf(HOST);    
	    if(index != -1) {
	    	index += HOST.length();
	    	host = authority = spec.substring(0, index);
	    	spec = spec.substring(index);
		}
	
    	index = nextUnit(spec);
    	if(index == -1) index = spec.length();
    	path = spec.substring(0, index);
    	spec = index == spec.length() ? "" : spec.substring(index);
	    	
    	if(!spec.isEmpty() && spec.length() > 1) {	
	    	do {
	    		index = nextUnit(spec);	   
	    		switch(spec.charAt(0) + "") {
	    			case REF:   ref   = spec.substring(1, index); break;
	    			case QUERY: query = spec.substring(1, index); break;
	    		}
	    		
	    		spec = index == spec.length() ? "" : spec.substring(index);
	    	} while(index != spec.length() && !spec.isEmpty());
	    }

	    
	    index = path.indexOf("/wiki/");
	    if(index != -1) {
	    	host = "en.wikipedia.org";
	    } else if(!path.contains("/")) {
	    	path = "/wiki/" + path;
	    	host = "en.wikipedia.org";
	    } else if(path.startsWith("/w/")) {
	    	host = "en.wikipedia.org";
    	} else {
    		host = "upload.wikimedia.org";
	    }

	    super.setURL(url, protocol, host, port, authority, userInfo, path, query, ref);
	    
//	    System.out.println(url.getProtocol());
//	    System.out.println(url.getAuthority());
//	    System.out.println(url.getHost());
//	    System.out.println(url.getUserInfo());
//	    System.out.println(url.getPath());
//	    System.out.println(url.getRef());
//	    System.out.println(url.getQuery());
//	    System.out.println();
//	    
//	    System.out.println(rawIn + " -> " + url.getPath());
//	    System.out.println("URL: " + url);
//	    System.out.println("\n\n\n");
	}
	
	private int nextUnit(String spec) {
		int index = spec.indexOf(REF, 1);
	    if(index == -1) index = spec.indexOf(QUERY, 1);
	    if(index == -1) index = spec.indexOf(HOST, 1);
	    if(index == -1) index = spec.length();
	    
	    return index;
	}
	
	protected String toExternalForm(URL url) {
		return url.getProtocol() + "://" + url.getHost()
					+ (url.getPath().startsWith("/") ? "" : "/") + url.getPath() 
					+ (url.getRef() != null ? REF + url.getRef() : "")
					+ (url.getQuery() != null ? QUERY + url.getQuery() : "");
		
//		return url.getProtocol() + "://" + url.getHost() + (url.getUserInfo().endsWith("/wiki/") ? "" : url.getPath())
//				+ (url.getRef() != null ? "#" + url.getRef(): "");
	}
}
