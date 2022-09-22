package snpc.generate.entity.hibernate;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.generator.ContentBasicApiGenerator;
import snpc.generate.entity.hibernate.generator.ContentEntityDTOGenerator;
import snpc.generate.entity.hibernate.generator.ContentEntityGenerator;
import snpc.generate.entity.hibernate.generator.ContentIntefaceServiceGenerator;
import snpc.generate.entity.hibernate.generator.ContentRepoGenerator;
import snpc.generate.entity.hibernate.generator.ContentServiceGenerator;
import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.ModelGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.util.ExportFileUtil;
import snpc.generate.entity.hibernate.util.PreprocessFileUtil;

public class GenerateMain {

	private static final String TASK_ENTITY = "entity";
	private static final String TASK_DTO = "dto";
	private static final String TASK_REPO = "repo";
	private static final String TASK_I_SERVICES = "iservices";
	private static final String TASK_SERVICES = "services";
	private static final String TASK_CONTROLLER = "controller";

	public static void main(String[] args) {

		/**
		 * PARAM
		 */
		String urlFileSQL = "C:\\Users\\Admin\\Documents\\dumps\\Dump20220919.sql"; // current gen only mysql export
																					// from database
		String urlFolder = "C:\\Users\\Admin\\Desktop\\temp\\export_code";
		String basePackage = "com.example.demo";

		/**
		 * LIST TASK
		 */
		List<String> taskExports = new ArrayList<>();
		taskExports.add(TASK_ENTITY);
//		taskExports.add(TASK_DTO);
//		taskExports.add(TASK_REPO);
//		taskExports.add(TASK_I_SERVICES);
//		taskExports.add(TASK_SERVICES);
//		taskExports.add(TASK_CONTROLLER);

		/**
		 * PREPROCESSING NOTE: current support only file sql export from mysql workbench. If you want change please read code :D
		 */
		List<String> lines = PreprocessFileUtil.preprocessFileSQL(urlFileSQL);
//		SEE FILE resource/file-sql-after-preprocessing.txt
//		for(String line: lines) {
//			System.out.println(line);
//		}
		List<EntityStruct> entites = ModelGenerator.generateFromMySql(lines);

		IContentGenerator generator = null;
		// execute task
		for (String task : taskExports) {

			// 1.choose generator and exactly folder export
			String urlFolderContent = urlFolder;
			String prefixNameFile = "";
			String suffixNameFile = "";
			switch (task) {
			case TASK_ENTITY:
				generator = new ContentEntityGenerator();
				urlFolderContent += "\\" + TASK_ENTITY;
				break;
			case TASK_DTO:
				generator = new ContentEntityDTOGenerator();
				urlFolderContent += "\\" + TASK_DTO;
				suffixNameFile = "DTO";
				break;
			case TASK_REPO:
				generator = new ContentRepoGenerator();
				urlFolderContent += "\\" + TASK_REPO;
				suffixNameFile = "Repo";
				break;
			case TASK_I_SERVICES:
				generator = new ContentIntefaceServiceGenerator();
				urlFolderContent += "\\" + TASK_SERVICES;
				prefixNameFile = "I";
				suffixNameFile = "Service";
				break;
			case TASK_SERVICES:
				generator = new ContentServiceGenerator();
				urlFolderContent += "\\" + TASK_SERVICES + "\\impl";
				suffixNameFile = "ServiceImpl";
				break;
			case TASK_CONTROLLER:
				generator = new ContentBasicApiGenerator();
				urlFolderContent += "\\" + TASK_CONTROLLER;
				suffixNameFile = "Controller";
				break;
			default:
				break;
			}
			// 2. generate file
			if (generator == null) {
				System.err.println("Not support other generator " + task);
				continue;
			}
			System.out.println();
			System.out.println("----------------------------------"+generator.getClass().getSimpleName()+"------------------------------------------------");
			for (EntityStruct entity : entites) {
				System.out.println(entity.toString());
				String content = generator.gen(basePackage, entity);
				if (content == null || content.equals("")) {
					System.out.println("Entity name = " + entity.getNameClass() + ", Generator class = "+ generator.getClass().getSimpleName() + "  -- Empty content");
					continue;
				}
				String nameFile = prefixNameFile + entity.getNameClass() + suffixNameFile + ".java";
//				ExportFileUtil.exportDataToFolder(urlFolderContent, nameFile, content);
//				System.out.println(content);
			}
			// 3. reset generator
			generator = null;
			prefixNameFile = "";
			suffixNameFile = "";
		}

//		System.out.println("============ OK ============");
//		System.out.println(urlFolder);
	}
}
