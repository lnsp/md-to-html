package mooxmirror.markhtml;

public class LineBreakElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<br>";
	}

	@Override
	public String getClosingTag() {
		return "";
	}

}
