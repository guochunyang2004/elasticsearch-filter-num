package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.charfilter.BaseCharFilter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by medcl on 15/12/24.
 */

public final class CAConvertCharFilter extends BaseCharFilter {

	private CAConvertType convertType = CAConvertType.CHINESE_TO_ARAB;

	private Reader transformedInput;

	public CAConvertCharFilter(Reader in, CAConvertType convertType) {
		super(in);
		this.convertType = convertType;
	}

	public int read(char[] cbuf, int off, int len) throws IOException {
		if (this.transformedInput == null) {
			this.fill();
		}

		return this.transformedInput.read(cbuf, off, len);
	}

	private void fill() throws IOException {
		StringBuilder buffered = new StringBuilder();
		char[] temp = new char[1024];

		for (int cnt = this.input.read(temp); cnt > 0; cnt = this.input.read(temp)) {
			buffered.append(temp, 0, cnt);
		}

		this.transformedInput = new StringReader(this.processPattern(buffered).toString());
	}

	public int read() throws IOException {
		if (this.transformedInput == null) {
			this.fill();
		}

		return this.transformedInput.read();
	}

	protected int correct(int currentOff) {
		return Math.max(0, super.correct(currentOff));
	}

	CharSequence processPattern(CharSequence input) {
		return CAConverter.getInstance().convert(convertType, input.toString());
	}
}
