package com.CASHe.Questionnaire.Utils;

import java.util.ArrayList;
import java.util.List;

public class CSVParser {

	public static List<String> csvParser(String csvRow) {
		
		String UTF8_BOM = "\uFEFF";
		
		if (csvRow.startsWith(UTF8_BOM)) {
			csvRow = csvRow.substring(1);
		}
		
		List<String> columnFields = new ArrayList<>();
		StringBuilder field = null;
		boolean isFieldStart = true;
		boolean isFieldEnd = false;
		boolean containsSpecialChar = false;
		for (int i=0; i<csvRow.length(); i++) {
			char currentLetter = csvRow.charAt(i);
			char nextLetter = (i+1 < csvRow.length()) ? csvRow.charAt(i+1) : '\n';
			
			// Start building fresh string if it is starting of field
			// reset isFieldStart
			if (isFieldStart) {
				field = new StringBuilder();
			}
			
			// If current letter is " and is starting of field then field contains special charachters (skip current ")
			// set containsSpecialChar
			if (currentLetter == '"' && isFieldStart) {
				containsSpecialChar = true;
				isFieldStart = false;
				continue;
			}
			
			isFieldStart = false;
			
			// If current letter is " and is not starting of field and next letter is not ", then the first "
			// is not serving as an escape for the second " and hence indicates the end of special field
			// that was started with an "
			// set isFieldEnd
			if (currentLetter == '"' && nextLetter != '"') {
				isFieldEnd = true;
				continue;
			}
			
			// If current letter is " and is not starting of field and next letter is also ", then the first "
			// is serving as an escape for the second " and therefore does not indicate end of special field
			if (currentLetter == '"' && nextLetter == '"') {
				i += 1;
				field.append(currentLetter);
				continue;
			}
			
			// If current letter is , and if it is either the end of a special field or is not part of a special
			// field, then it indicates the end of the field, therefore add the string formed till now, to the 
			// list of strings (columnFields)
			// reset containsSpecialChar
			// set isFieldStart
			boolean commaEndOfSpecialField = containsSpecialChar && isFieldEnd;
			boolean commaNotBelongToSpecialField = !containsSpecialChar;
			if (currentLetter == ',' && (commaEndOfSpecialField || commaNotBelongToSpecialField)) {
				columnFields.add(field.toString());
				containsSpecialChar = false;
				isFieldStart = true;
				isFieldEnd = false;
				continue;
			}
			
			// If none of the above special conditions hit, the keep building the string
			field.append(currentLetter);
		}
		
		// Adding the last field that was constructed in the stringbuilder
		columnFields.add(field.toString());
		
		return columnFields;
	}
	
}
