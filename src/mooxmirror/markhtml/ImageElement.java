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

public class ImageElement extends Element {
	private String altText;
	private String imageUrl;
	private String title;

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public void setImageURL(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAltText() {
		return altText;
	}

	public String getImageURL() {
		return imageUrl;
	}

	@Override
	public String getOpeningTag() {
		return "<img src=\"" + imageUrl + "\" alt=\"" + altText
				+ "\" + title=\"" + title + "\">";
	}

	@Override
	public String getClosingTag() {
		return "</img>";
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
}
