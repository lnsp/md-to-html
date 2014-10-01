package mooxmirror.markhtml;

public class BlockquoteElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<blockquote>";
	}

	@Override
	public String getClosingTag() {
		return "</blockquote>";
	}

}
