package mooxmirror.markhtml;

public class HeadingElement extends Element {
	private int level;

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	@Override
	public String getOpeningTag() {
		return "<h" + level + ">";
	}

	@Override
	public String getClosingTag() {
		return "</h" + level + ">";
	}
}
