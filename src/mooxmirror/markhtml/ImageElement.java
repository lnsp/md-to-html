package mooxmirror.markhtml;

public class ImageElement extends Element {
	private String altText;
	private String imageUrl;
	private String title;

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public void setImageURL(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAltText() {
		return altText;
	}

	public String getImageURL() {
		return imageUrl;
	}

	@Override
	public String getOpeningTag() {
		return "<img src=\"" + imageUrl + "\" alt=\"" + altText
				+ "\" + title=\"" + title + "\">";
	}

	@Override
	public String getClosingTag() {
		return "</img>";
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
