package mooxmirror.markhtml;

public class ListItemElement extends Element {
	@Override
	public String getOpeningTag() {
		return "<li>";
	}

	@Override
	public String getClosingTag() {
		return "</li>";
	}
}
