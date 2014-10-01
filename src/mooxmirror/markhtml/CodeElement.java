package mooxmirror.markhtml;

public class CodeElement extends Element {

	@Override
	public String getOpeningTag() {
		return "<pre><code>";
	}

	@Override
	public String getClosingTag() {
		return "</code></pre>";
	}

}
