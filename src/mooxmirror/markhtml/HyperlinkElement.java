package mooxmirror.markhtml;

public class HyperlinkElement extends Element {
	private String hoverText;
	private String linkUrl;

	public String getHoverText() {
		return hoverText;
	}

	public String getLinkURL() {
		return linkUrl;
	}

	public void setHoverText(String hoverText) {
		this.hoverText = hoverText;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	@Override
	public String getOpeningTag() {
		return "<a href=\"" + linkUrl + "\" alt=\"" + hoverText + "\">";
	}

	@Override
	public String getClosingTag() {
		return "</a>";
	}
}
