package window.browser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WikipediaURLConnection extends URLConnection {

	private static final String[] keys = {
			"<HTML", 
			"<HEAD", "</HEAD",
			"id=\"content\"", "id=\"mw-navigation\"",
			"</HTML"
		};
	
	private InputStream stream;
	private URL actualURL;
	
	// wikipedia/commons/thumb/e/eb/Indo-European_branches_map.svg/300px-Indo-European_branches_map.svg.png
	public WikipediaURLConnection(URL url) {
		super(url);
		try { actualURL = new URL("https", url.getHost(), 
					  (url.getPath().startsWith("/") ? "" : "/") + url.getPath()
					+ (url.getRef() != null ? "#" + url.getRef() : "")
					+ (url.getQuery() != null ? "?" + url.getQuery() : "")
				); 
		
		} catch(MalformedURLException e) { e.printStackTrace(); }
//		System.out.println(actualURL);
	}
	
	public InputStream getInputStream() { return stream; }

	public void connect() throws IOException {
		if(!super.url.getPath().startsWith("/wiki/")) {
			stream = actualURL.openStream();
			return;
		}

		int index = 0;
		String line = "";
		boolean skipping = false;

		BufferedReader reader = new BufferedReader(new InputStreamReader(actualURL.openStream()));
		StringBuilder builder = new StringBuilder();
		
//		String lastLine = ""; boolean printed = false;
		while((line = reader.readLine()) != null) {		
//			if(!printed)
//				System.err.println(lastLine);
//			printed = false;
//			lastLine = line;
			
			boolean contained = false;
			if(line.toLowerCase().contains(keys[index].toLowerCase())) {
				skipping = !skipping;
				index ++;
				
				postIndexMod:
				if(index == 4) {
					int classIndex = line.indexOf("class=");
					if(classIndex == -1) break postIndexMod;
					int nextQ = line.indexOf("\"", classIndex + "class=\"".length());
					line = line.replaceAll(line.substring(classIndex, nextQ + 1), "");
				} else if(index == 5)
					continue;
				contained = true;
			}
			
			if(skipping && !contained) continue;
			builder.append(processesLine(line));
			builder.append("\n");
			
//			System.out.println(line);
//			printed = true;
		}
		
		reader.close();
		stream = new ByteArrayInputStream(builder.toString().getBytes());	
		
//		stream = new ByteArrayInputStream(("<!DOCTYPE html>" +
//					"<HTML>" +
//                    	"\t<head>" +
//                    		"\t\t<link rel=\"stylesheet\" href=\"https://en.wikipedia.org/w/load.php?debug=false&lang=en&modules=ext.cite.styles%7Cext.gadget.DRN-wizard%2CReferenceTooltips%2Ccharinsert%2Cfeatured-articles-links%2CrefToolbar%2Cswitcher%2Cteahouse%7Cext.tmh.thumbnail.styles%7Cext.uls.nojs%7Cext.visualEditor.desktopArticleTarget.noscript%7Cext.wikimediaBadges%7Cmediawiki.legacy.commonPrint%2Cshared%7Cmediawiki.raggett%2CsectionAnchor%7Cmediawiki.skinning.interface%7Cskins.vector.styles%7Cwikibase.client.init&only=styles&skin=vector\">" +
//                    	
//							"\t\t<Title>" +
//								"\t\t\t Test HTML" +
//							"\t\t</Title>" +
//                    	"\t</head>" +
//                    		
//                    	"\t<body>" +
//                    		"\t\t<div id=\"toc\"class=\"toc\">" + 
//                    			"\t\t\t This is a Test" + 
//                    		"\t\t</p>" + 
//                    	"\t<body>" +
//                    "</HTML>").getBytes(StandardCharsets.UTF_8));
	}
	
	private String processesLine(String line) {
//		int index = line.indexOf("/w/");
//		if(index != -1) {
//			if(line.charAt(index - 1) == 'g') return line;
//			line = line.replace("/w/", "http://en.wikipedia.org/w/");
//		}
		
		return line;
	}
}
