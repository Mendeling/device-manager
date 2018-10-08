package common.tools.dbtools.scaffold;


import org.apache.commons.lang3.StringUtils;

public class UncapitalizeWordsParser implements WordsParser {

    @Override
	public String parseWords(String orginalString) {
		return StringUtils.uncapitalize(orginalString);
	}

}
