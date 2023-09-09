package snpc.generate.entity.hibernate.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {

	public static String nameToNameClass(String name) {
		String result = "";
		String sRegex = "\\s+|_+|-+";
		String[] els = name.split(sRegex);
		for (String el : els) {
			result = result + StringUtils.capitalize(el);
		}
		return result;
	}

	public static String nameToNameProperties(String name) {
		String b = "";
		String sRegex = "\\s+|_+|-+";
		String[] els = name.split(sRegex);
		for (String el : els) {
			b = b + StringUtils.capitalize(el);
		}
		// lower first letter
		char c[] = b.toCharArray();
		c[0] += 32;
		return new String(c);
	}
	public static String nameToNamePropertiesButRemoveEndId(String name){
		String afterChange = nameToNameProperties(name);
		if(afterChange.endsWith("Id")) {
			return afterChange.substring(0, afterChange.length() - 2);
		} else if(afterChange.endsWith("Ids"))  {
			return afterChange.substring(0, afterChange.length() - 3);
		}
		return afterChange;
	}

	public static String lowerFirstLetter(String name) {
		char c[] = name.toCharArray();
		c[0] += 32;
		return new String(c);
	}
}