package cn.bdqn.ssm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringtoDateConverter implements Converter<String,Date>{



	private String datePattern;//要转换的日期格式

	public StringtoDateConverter(String datePattern) {
		System.out.println(datePattern);
		this.datePattern = datePattern;
	}



	public Date convert(String s) {
		Date date = null;
		try {
			date = new SimpleDateFormat(datePattern).parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


}
