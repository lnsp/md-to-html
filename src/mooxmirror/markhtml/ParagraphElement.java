package mooxmirror.markhtml;

public class ParagraphElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<p>";
	}

	@Override
	public String getClosingTag() {
		return "</p>";
	}

}
