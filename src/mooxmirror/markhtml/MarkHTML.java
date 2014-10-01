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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.List;

public class MarkHTML {
	private Context markdownContext;

	public static void main(String[] args) throws IOException {
		System.out.println("MarkHTML by Mooxmirror | github.com/mooxmirror");
		String documentName = "MarkHTML Document";
		String stylesheet = "default.css";

		if (args.length > 3) {
			stylesheet = args[3];
			documentName = args[2];
			System.out.println("Document title: " + documentName);
			System.out.println("Stylesheet: " + stylesheet);
		} else if (args.length > 2) {
			documentName = args[2];
			System.out.println("Document title: " + documentName);
		} else if (args.length > 1) {
			System.out.println("Default stylesheet and document title.");
		} else {
			System.err
					.println("You need to include the source and destination file path.");
			System.exit(0);
		}

		MarkHTML mark = new MarkHTML();
		List<String> data = Files.readAllLines(FileSystems.getDefault()
				.getPath(args[0]));
		String style = new String(Files.readAllBytes(FileSystems.getDefault()
				.getPath("default.css")));
		try {
			String r = mark.convert(data, "My Document", style, true);
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			writer.write(r);
			writer.close();

			System.out.println("Parsing successful.");
		} catch (Exception e) {
			System.out.println(Context.getCurrentLine());
			e.printStackTrace();
		}

	}
	public String convert(List<String> source, String documentTitle,
			String style, boolean sign) {
		markdownContext = Context.parse(source);

		StringBuilder builder = new StringBuilder();
		builder.append("<html><head><title>" + documentTitle
				+ "</title><style>" + style + "</style></head><body>");
		createHTML(getContext().getRootElement(), builder);
		if (sign)
			builder.append("<footer><i>Generated with <a href=\"https://github.com/mooxmirror/markhtml\">MarkHTML</a></i></footer>");
		builder.append("</body></html>");
		return builder.toString();
	}

	public void createHTML(Element r, StringBuilder t) {
		for (Element e : r.getElements()) {
			t.append(e.getOpeningTag());
			if (e.getElements().size() > 0)
				createHTML(e, t);
			else
				t.append(e.getText());
			t.append(e.getClosingTag());
		}
	}

	public Context getContext() {
		return markdownContext;
	}
}
