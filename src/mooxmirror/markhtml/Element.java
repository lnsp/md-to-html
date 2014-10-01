/*Copyright 2014 Mooxmirror

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.*/

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
