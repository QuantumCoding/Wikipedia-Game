package interfaces;

import java.util.ArrayList;

public interface IWebBrowser {
	public String getSite();
	public void setSite(String site);
	public void addSiteChangeListener(ISiteChangeListener listener);
	
	public ArrayList<String> getHistory();
	public void clearHistory();
}
