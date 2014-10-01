package mooxmirror.markhtml;

public class BoldItalicTextElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<b><i>";
	}

	@Override
	public String getClosingTag() {
		return "</b></i>";
	}

}
