package snpc.generate.entity.hibernate.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ExportFileUtil {
	public static void exportDataToFolder(String urlFolder, String fileName, String data) {
		File dir = new File(urlFolder);
		if (dir.exists() == false) {
			dir.mkdirs();
		}
		File file = new File(urlFolder + "\\" + fileName);
		try (FileWriter fWriter = new FileWriter(file); BufferedWriter bWriter = new BufferedWriter(fWriter)) {
			bWriter.write(data);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
