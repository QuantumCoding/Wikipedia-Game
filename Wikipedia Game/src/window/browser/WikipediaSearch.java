package window.browser;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

public class WikipediaSearch {
	private static final String GOOGLE_ROOT_URL = "https://www.google.com/search?q=site:en.wikipedia.org+";
	
	public static Collection<String> lookUp(String search) {
		try {
			Tidy tidy = new Tidy();
			tidy.setShowWarnings(false);
			tidy.setShowErrors(0);
			tidy.setXHTML(true);
			tidy.setQuiet(true);
			
			URLConnection connection = new URL(GOOGLE_ROOT_URL + search.replaceAll(" ", "+")).openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connection.connect();
			
			Document document = tidy.parseDOM(connection.getInputStream(),null);
			Node resultsParent = getNodeByProperty(document, "class", "srg");
			if(resultsParent == null) return new ArrayList<>();
			
			ArrayList<String> results = new ArrayList<>();
			NodeList resultingNodes = resultsParent.getChildNodes();
			Node nextNode = resultingNodes.item(0);
			
			do {
				StringBuilder resultBuilder = new StringBuilder();
				Node cite = getNodeByProperty(nextNode, "class", "_Rm");
				Node textNode = cite.getFirstChild();
				
				do {
					if(textNode.getLocalName().equals("#text")) 
						resultBuilder.append(textNode.getNodeValue());
					
					textNode = findNextNode(textNode, cite);					
				} while(textNode != cite);
				
				
				String resultCite = resultBuilder.toString();
				resultCite = URLDecoder.decode(resultCite, "UTF-8");
				
				int wikiIndex = resultCite.indexOf("wiki/");
				if(wikiIndex == -1) continue;
				resultCite = resultCite.substring(wikiIndex + 5);
				
				results.add(resultCite.replace("_", " "));
			} while((nextNode = nextNode.getNextSibling()) != null);
			
			return results;
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return Collections.singleton("ERROR: Fail to Search");
	}
	
	private static Node findNextNode(Node start, Node limit) {
		if(start == limit) return limit;
		
		if(start.getFirstChild() != null)
			return start.getFirstChild();
		else if(start.getNextSibling() != null)
			return start.getNextSibling();
	
		do {
			start = start.getParentNode();
			if(start == limit) return limit;
		} while(start.getNextSibling() == null);
		
		return start.getNextSibling();
	}
	
	private static Node getNodeByProperty(Node root, String property, String value) {
    	if(root == null) return null;
    	if(checkProperty(root, property, value)) return root;
    	
    	NodeList list = root.getChildNodes();
    	for(int i = 0; i < list.getLength(); i ++) {
			Node node = getNodeByProperty(list.item(i), property, value);
			if(node != null) return node;
		}
    	
    	return null;
    }
	
	private static boolean checkProperty(Node root, String property, String value) {
    	if(root == null) return false;
    	
    	NamedNodeMap attributes = root.getAttributes();
    	if(attributes == null) return false;
    	
    	Node node = attributes.getNamedItem(property);
    	if(node == null) return false;
    	
    	if(node.getNodeValue().equals(value))
    		return true;
    	return false;
    }
}
