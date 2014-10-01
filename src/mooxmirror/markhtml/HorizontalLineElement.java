package mooxmirror.markhtml;

public class HorizontalLineElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<hr>";
	}

	@Override
	public String getClosingTag() {
		return "</hr>";
	}

}
