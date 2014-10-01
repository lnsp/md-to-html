package mooxmirror.markhtml;

public class BoldTextElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<b>";
	}

	@Override
	public String getClosingTag() {
		return "</b>";
	}

}
