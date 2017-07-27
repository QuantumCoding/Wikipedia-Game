package interfaces;

public interface IWebBrowser {
	public String getSite();
	public void setSite(String site);
	public void addSiteChangeListener(ISiteChangeListener listener);
	public void addLoadListener(ILoadListener listener);
	
	public void enableSearch(boolean enable);
}
