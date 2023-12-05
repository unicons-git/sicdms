package com.g3way.sicims.util.common;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class CustomPropertyEditor extends PropertyEditorSupport {

	private final String dataType;

	@SuppressWarnings("unused")
	private final DateFormat dateFormat;


	public CustomPropertyEditor(String dataType) {
		this.dataType = dataType;
		this.dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
	}

	public CustomPropertyEditor(String dataType, DateFormat dateFormat) {
		this.dataType = dataType;
		this.dateFormat = dateFormat;
	}

	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(!StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else if(this.dataType.equals("BigDecimal")) {
			String val = text;
			val = val.replaceAll("\\,", "");
			try {
	        	BigDecimal bigDecimal = new BigDecimal(val);
	        	setValue(bigDecimal);
	        } catch (NumberFormatException ex) {
	            setValue(null);
	            throw new IllegalArgumentException("Could not parse Bigdecimal: " + ex.getMessage(), ex);
	        }
		}
		else if (this.dataType.equals("Date")) {
			try {
                setValue(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).parse(text));
            } catch (ParseException ex) {
            	setValue(null);
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
    }
}