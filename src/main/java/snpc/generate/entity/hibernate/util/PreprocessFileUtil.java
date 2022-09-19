package snpc.generate.entity.hibernate.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class PreprocessFileUtil {
	public static List<String> preprocessFileSQL(String url) {
		List<String> result = new ArrayList<String>();
		try (FileReader fReader = new FileReader(new File(url)); BufferedReader bReader = new BufferedReader(fReader)) {
			String line;
			while((line = bReader.readLine()) != null) {
				line = line.trim();
				// case continue
				if(line.equals("") 
						|| line.startsWith("CREATE DATABASE") 
						|| line.startsWith("--") 
						|| line.startsWith("/*") 
						|| line.startsWith("DROP TABLE")
						|| line.startsWith(")")
						|| line.startsWith("USE")
						|| line.startsWith("KEY")
						|| line.startsWith("SPATIAL")
						|| line.startsWith("UNIQUE KEY")
						|| line.startsWith("FULLTEXT KEY")) {
					continue;
				}
				line = line.replaceAll("`", "");
				line = line.replaceAll("\\s+", " ");
				line = line.replaceAll("\\/.+", "");
				result.add(line);
			}
		} catch (Exception e) {
			System.out.println("File is not exist !");
		}
		return result;
	}
}
