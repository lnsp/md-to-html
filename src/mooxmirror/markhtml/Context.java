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

import java.util.List;

public class Context {
	private static String currentLine;
	private RootElement rootElement;

	public Context() {
		this.rootElement = new RootElement();
	}

	public RootElement getRootElement() {
		return rootElement;
	}

	public static String getCurrentLine() {
		return currentLine;
	}
	public static void setCurrentLine(String line) {
		currentLine = line;
	}
	public static Context parse(List<String> document) {
		Context context = new Context();
		Element activeElement = context.getRootElement();
		int lineCount = document.size();
		int lineIndex = 0;

		for (String line : document) {
			Context.setCurrentLine(line);
			if (activeElement instanceof CodeElement) {
				if (line.startsWith("```")) {
					activeElement = activeElement.getRootElement();
				} else {
					PlainTextElement e = new PlainTextElement();
					e.setText(line.replaceAll("\\s{4}", "\t"));
					activeElement.addElement(e);
					activeElement.addElement(new LineBreakElement());
				}
			} else if (line.equals("")) {
				if (activeElement.hasRootElement())
					activeElement = activeElement.getRootElement();

			} else if (line.startsWith("```")) {
				CodeElement c = new CodeElement();
				activeElement.addElement(c);
				activeElement = c;

			} else if (line.matches("^={1,}")) {
				if (activeElement instanceof ParagraphElement) {
					int c = activeElement.getElements().size();
					if (c == 0) {
						activeElement.addElement(new HorizontalLineElement());
					} else {
						Element inline = activeElement.getElements().get(c - 1);

						HeadingElement h = new HeadingElement();
						h.setLevel(1);
						h.addElement(inline);

						activeElement.getElements().remove(c - 1);
						Element root = activeElement.getRootElement();
						root.addElement(h);
						activeElement = root;
					}
				}
			} else if (line.matches("^-{1,}")) {
				if (activeElement instanceof ParagraphElement) {
					int c = activeElement.getElements().size();
					if (c == 0) {
						activeElement.addElement(new HorizontalLineElement());
					} else {
						Element inline = activeElement.getElements().get(c - 1);

						HeadingElement h = new HeadingElement();
						h.setLevel(2);
						h.addElement(inline);

						activeElement.getElements().remove(c - 1);
						Element root = activeElement.getRootElement();
						root.addElement(h);
						activeElement = root;
					}
				}
			} else if (line.matches("^\\d{1,}\\.{1}.{1,}")) {
				ListItemElement item = new ListItemElement();
				int digitCount = context.startDigitCount(line);
				Element t = context.parseInlineElement(line.substring(
						digitCount
								+ 1
								+ context.startChars(' ',
										line.substring(digitCount + 1)),
						line.length()));
				item.addElement(t);

				if (activeElement instanceof SortedListElement) {
					activeElement.addElement(item);
				} else {
					SortedListElement sortedList = new SortedListElement();
					sortedList.addElement(item);

					activeElement.addElement(sortedList);
					activeElement = sortedList;
				}
			} else if (line.matches("^[*]{1}[^*]{1}.{1,}")
					|| line.matches("^[-]{1}[^-]{1}.{1,}")
					|| line.matches("^[+]{1}[^+]{1}.{1,}")) {
				ListItemElement item = new ListItemElement();

				int spaceCounts = context.countChars(line, ' ', 1);
				Element e = context.parseInlineElement(line
						.substring(spaceCounts + 1));
				item.addElement(e);
				if (activeElement instanceof UnsortedListElement) {
					activeElement.addElement(item);
				} else {
					UnsortedListElement sortedList = new UnsortedListElement();
					sortedList.addElement(item);

					activeElement.addElement(sortedList);
					activeElement = sortedList;
				}
			} else if (line.matches("#{1,}.{1,}")) {
				int level = context.startChars('#', line);
				HeadingElement heading = new HeadingElement();
				activeElement.addElement(heading);
				heading.setLevel(level);
				Element e = context.parseInlineElement(line.substring(level
						+ context.startChars(' ', line.substring(level)),
						line.length()));
				heading.addElement(e);
			} else if (line.matches(".{1,}\\s{2,}")) {
				if (activeElement instanceof ParagraphElement) {
					Element text = context.parseInlineElement(line.substring(0,
							context.endChars(' ', line)));
					LineBreakElement breakElement = new LineBreakElement();
					text.addElement(breakElement);
					activeElement.addElement(text);
				}
			} else {
				if (activeElement instanceof ParagraphElement) {
					Element t = context.parseInlineElement(line);
					t.setText(line.substring(0, context.endChars(' ', line)));
					activeElement.addElement(t);
				} else {
					ParagraphElement passage = new ParagraphElement();
					Element text = context.parseInlineElement(line);
					passage.addElement(text);
					activeElement.addElement(passage);
					activeElement = passage;
				}
			}

			System.out.println("Processing: "
					+ (int) ((double) lineIndex / (double) lineCount * 100)
					+ "%");
			lineIndex++;
		}

		return context;
	}
	public int endChars(char c, String line) {
		int count = 0;
		for (int i = line.length() - 1; i < line.length(); i--) {
			if (line.charAt(i) == c)
				count++;
			else
				return count;
		}
		return 0;
	}

	public int startChars(char c, String line) {
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == c)
				count++;
			else
				return count;
		}
		return 0;
	}

	public int startDigitCount(String line) {
		int count = 0;
		for (int i = 0; i < line.length(); i++) {
			if (Character.isDigit(line.charAt(i)))
				count++;
			else
				return count;
		}
		return 0;
	}

	public int countAppearances(String line, char c) {
		int count = 0;
		int lastCount = 0;
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == c && i - 1 != lastCount) {
				count++;
				lastCount = i;
			}
		}
		return count;
	}
	public int countChars(String line, char c, int offset) {
		int count = 0;
		for (int i = offset; i < line.length(); i++) {
			if (line.charAt(i) == c)
				count++;
			else
				return count;
		}
		return 0;
	}
	public void parseTextElement(Element root, String line) {
		InlineElement e = new InlineElement();
		if (line.matches(".{0,}!{1}\\[{1}.{1,}\\]{1}\\({1}.{1,}\\s{0,1}\"{0,1}.{0,1}\"{0,1}\\){1}.{0,}")) {
			int openTextBracket = line.indexOf("![");
			int closeTextBracket = line.indexOf(']', openTextBracket);
			if (openTextBracket > 0) {
				parseTextElement(e, line.substring(0, openTextBracket));
			}
			ImageElement a = new ImageElement();
			String altText = line.substring(openTextBracket + 2,
					closeTextBracket);

			a.setText("");
			a.setAltText(altText);
			int openURLBracket = line.indexOf('(', closeTextBracket);
			int closeURLBracket = line.indexOf(' ', openURLBracket);
			int closeExpressionBracket = line.indexOf(')', openURLBracket);
			if (closeURLBracket < 0)
				closeURLBracket = closeExpressionBracket;

			String imageURL = line.substring(openURLBracket + 1,
					closeURLBracket);
			a.setImageURL(imageURL);
			if (closeURLBracket != closeExpressionBracket) {
				int openTitleBracket = line.indexOf('"', closeURLBracket);
				int closeTitleBracket = line.indexOf('"', openTitleBracket);
				String titleText = line.substring(openTitleBracket + 1,
						closeTitleBracket);
				a.setTitle(titleText);
			} else
				a.setTitle(altText);

			if (closeURLBracket + 1 < line.length()) {
				parseTextElement(e, line.substring(closeURLBracket + 1));
			}

			e.addElement(a);
		} else if (line
				.matches(".{0,}\\[{1}.{1,}\\]{1}\\({1}.{1,}\\s{0,1}\"{0,1}.{0,1}\"{0,1}\\){1}.{0,}")) {
			// link

			int openTextBracket = line.indexOf('[');
			int closeTextBracket = line.indexOf(']', openTextBracket);
			if (openTextBracket > 0) {
				parseTextElement(e, line.substring(0, openTextBracket));
			}
			HyperlinkElement a = new HyperlinkElement();
			String text = line.substring(openTextBracket + 1, closeTextBracket);

			a.setText(text);
			int openLinkBracket = line.indexOf('(', closeTextBracket);
			int closeLinkBracket = line.indexOf(' ', openLinkBracket);
			int closeExpressionBracket = line.indexOf(')', openLinkBracket);
			if (closeLinkBracket < 0
					|| closeLinkBracket > closeExpressionBracket)
				closeLinkBracket = closeExpressionBracket;

			String link = line.substring(openLinkBracket + 1, closeLinkBracket);
			a.setLinkUrl(link);
			int openHoverBracket = line.indexOf('"', closeLinkBracket);
			int closeHoverBracket = line.indexOf('"', openHoverBracket);
			if (closeLinkBracket != closeExpressionBracket
					&& openHoverBracket > -1 && closeHoverBracket > -1) {
				String hoverText = line.substring(openHoverBracket + 1,
						closeHoverBracket);
				a.setHoverText(hoverText);
			}

			e.addElement(a);
			if (closeLinkBracket + 1 < line.length()) {
				parseTextElement(e, line.substring(closeLinkBracket + 1));
			}

		} else {
			PlainTextElement t = new PlainTextElement();
			t.setText(line);
			e.addElement(t);
		}
		root.addElement(e);
	}
	public Element parseInlineElement(String line) {
		InlineElement text = new InlineElement();

		Element textElement = null;
		if (line.matches(".{0,}\\*{1,3}.{1,}\\*{1,3}.{0,}")) {
			int count = countAppearances(line, '*');

			int startCharIndex = line.indexOf('*');
			int startTextIndex = 0;

			for (int i = 0; i < count; i++) {
				if (startCharIndex > startTextIndex) {
					parseTextElement(text,
							line.substring(startTextIndex, startCharIndex));
					startTextIndex = startCharIndex;
				}
				if (textElement == null) {
					int charCount = countChars(line, '*', startCharIndex);
					if (charCount == 1) {
						textElement = new ItalicTextElement();
					}
					if (charCount == 2) {
						textElement = new BoldTextElement();
					}
					if (charCount == 3) {
						textElement = new BoldItalicTextElement();
					}
					startTextIndex = startCharIndex + charCount;
				} else {
					int endTextIndex = line.indexOf('*', startTextIndex);

					parseTextElement(textElement,
							line.substring(startTextIndex, endTextIndex));
					text.addElement(textElement);
					startTextIndex = endTextIndex
							+ countChars(line, '*', endTextIndex);
					startCharIndex = line.indexOf('*', startTextIndex);
					if (startCharIndex == -1)
						break;
					textElement = null;

				}
			}
			if (startTextIndex < line.length()) {
				parseTextElement(text,
						line.substring(startTextIndex, startCharIndex));
			}

		} else if (line.matches("^>.{1,}")) {
			textElement = new BlockquoteElement();
			parseTextElement(textElement, line.substring(1));
			text.addElement(textElement);
		} else if (line.matches("\\s{4,}.{1,}")) {
			textElement = new BlockquoteElement();
			CodeElement code = new CodeElement();
			code.setText(line.substring(startChars(' ', line)));
			textElement.addElement(code);
			text.addElement(textElement);
		} else {
			textElement = new PlainTextElement();
			parseTextElement(textElement, line);
			text.addElement(textElement);
		}
		return text;
	}
}
