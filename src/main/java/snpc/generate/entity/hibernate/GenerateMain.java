package snpc.generate.entity.hibernate;

import java.util.List;

import snpc.generate.entity.hibernate.generator.ContentEntityGenerator;
import snpc.generate.entity.hibernate.generator.ContentRepoGenerator;
import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.ModelGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.util.PreprocessFileUtil;

public class GenerateMain {
	public static void main(String[] args) {
		String urlFileSQL = "C:\\Users\\Admin\\Documents\\dumps\\Dump20220919.sql"; // current gen only mysql export from database
		List<String> lines = PreprocessFileUtil.preprocessFileSQL(urlFileSQL);
//		for(String line : lines) {
//			System.out.println(line);
//		}
		List<EntityStruct> entites = ModelGenerator.generateFromMySql(lines);
		IContentGenerator generator = new ContentRepoGenerator();
		for (EntityStruct entity : entites) {
			String content = generator.gen("snpc.gen", entity);
			System.out.println(content);
		}

	}
}
