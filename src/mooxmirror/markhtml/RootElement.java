package mooxmirror.markhtml;

public class RootElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<body>";
	}

	@Override
	public String getClosingTag() {
		return "</body>";
	}

}
