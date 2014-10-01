MarkHTML
========

Fast-paced Markdown to HTML converter written in Java.

### Progress
* Paragraphs ✓
* Formatting ✓
* Hyperlinks ✓
* Headings ✓
* Linebreaks ✓
* (Un)ordered lists
* Multi-line code
* Images
* Indented lists
* Automatic hyperlink parsing

### Running MarkHTML
`markhtml [source] [destination]`

### Using MarkHTML as a parser
If you want to use MarkHTML just as a parser, then do it. The tree-structure of the elements should be easy to use for any structured markup language. When you know, which language you want to implement, just call  `Context.parse(source)` and let me see the results.

[The license](LICENSE.TXT) | [My homepage](https://mooxmirror.github.io)
