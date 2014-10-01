package mooxmirror.markhtml;

public class ItalicTextElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<i>";
	}

	@Override
	public String getClosingTag() {
		return "</i>";
	}

}
