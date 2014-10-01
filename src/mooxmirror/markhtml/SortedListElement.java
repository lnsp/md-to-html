package mooxmirror.markhtml;

public class SortedListElement extends Element {
	private boolean sorted;

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	@Override
	public String getOpeningTag() {
		return "<ol>";
	}

	@Override
	public String getClosingTag() {
		return "</ol>";
	}
}
