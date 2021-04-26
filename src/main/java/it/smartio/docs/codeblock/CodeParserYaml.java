/*
 * Copyright (c) 2001-2021 Territorium Online Srl / TOL GmbH. All Rights Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as defined in and that are
 * subject to the Territorium Online License Version 1.0. You may not use this file except in
 * compliance with the License. Please obtain a copy of the License at http://www.tol.info/license/
 * and read it before using this file.
 *
 * The Original Code and all software distributed under the License are distributed on an 'AS IS'
 * basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, AND TERRITORIUM ONLINE HEREBY
 * DISCLAIMS ALL SUCH WARRANTIES, INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT. Please see the License for
 * the specific language governing rights and limitations under the License.
 */

package it.smartio.docs.codeblock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.smartio.docs.builder.CodeParserDefault;
import it.smartio.docs.builder.SectionBuilder;

/**
 * The {@link CodeParserYaml} class.
 */
class CodeParserYaml extends CodeParserDefault {

	private static final String PATTERN_TEXT = "^(?:(?:([^:']*):)?([^#]*))(#.+)?$";
	private static final Pattern PATTERN = Pattern.compile(CodeParserYaml.PATTERN_TEXT, Pattern.CASE_INSENSITIVE);

	/**
	 * Constructs an instance of {@link CodeParserYaml}.
	 *
	 * @param builder
	 */
	public CodeParserYaml(SectionBuilder builder) {
		super(builder);
	}

	/**
	 * Parses the code text
	 *
	 * @param node
	 */
	@Override
	public void parse(String text) {
		setFontSize("10pt");
		setTextColor(CodeToken.YAML_COLOR.COLOR);
		setBorderColor(CodeToken.YAML_COMMENT.COLOR);
		setBackground(CodeToken.YAML_BACKGROUND.COLOR);

		for (String line : text.split("\\n")) {
			Matcher matcher = CodeParserYaml.PATTERN.matcher(line);
			if (matcher.find()) {
				if (matcher.group(1) != null) { // Parameter
					addInline(matcher.group(1)).setColor(CodeToken.YAML_ATTR.COLOR);
					addText(":");
				}
				if (matcher.group(2) != null) { // Value
					addInline(matcher.group(2)).setColor(CodeToken.YAML_VALUE.COLOR);
				}

				if (matcher.group(3) != null) { // Comment
					addInline(line).setItalic().setColor(CodeToken.YAML_COMMENT.COLOR);
				}
			} else {
				addText(line);
			}
			addText("\n");
		}
	}
}
