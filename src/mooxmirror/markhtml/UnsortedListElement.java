package mooxmirror.markhtml;

public class UnsortedListElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<ul>";
	}

	@Override
	public String getClosingTag() {
		return "</ul>";
	}

}
