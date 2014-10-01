package mooxmirror.markhtml;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
	private List<Element> subElements;
	private Element rootElement;
	private String text;

	public Element() {
		this.subElements = new ArrayList<Element>();
		this.text = "";
	}

	public boolean hasRootElement() {
		return rootElement != null;
	}

	public void setRootElement(Element e) {
		this.rootElement = e;
	}

	public Element getRootElement() {
		return rootElement;
	}

	public List<Element> getElements() {
		return subElements;
	}

	public void addElement(Element e) {
		e.setRootElement(this);
		subElements.add(e);
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public abstract String getOpeningTag();
	public abstract String getClosingTag();
}
