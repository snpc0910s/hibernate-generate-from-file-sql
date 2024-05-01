package snpc.generate.entity.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.ModelGenerator;
import snpc.generate.entity.hibernate.generator.OptionalConfig;
import snpc.generate.entity.hibernate.generator.controller.ContentBasicApiGenerator;
import snpc.generate.entity.hibernate.generator.dto.ContentEntityDTOGenerator;
import snpc.generate.entity.hibernate.generator.entity.ContentEntityGenerator;
import snpc.generate.entity.hibernate.generator.entity.ContentEntityRelationshipGenerator;
import snpc.generate.entity.hibernate.generator.repository.ContentRepoCustomGenerator;
import snpc.generate.entity.hibernate.generator.repository.ContentRepoGenerator;
import snpc.generate.entity.hibernate.generator.repository.custom.ContentRepoCustomImplGenerator;
import snpc.generate.entity.hibernate.generator.service.ContentIntefaceServiceGenerator;
import snpc.generate.entity.hibernate.generator.service.ContentServiceGenerator;
import snpc.generate.entity.hibernate.generator.service.ContentServiceRelationshipGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.util.ExportFileUtil;
//import snpc.generate.entity.hibernate.util.ExportFileUtil;
import snpc.generate.entity.hibernate.util.PreprocessFileUtil;

public class GenerateMain {

	public static final String TASK_ENTITY = "entity";
	public static final String TASK_ENTITY_RELATIONSHIP = "entity_relationship";
	public static final String TASK_DTO = "dto";
	public static final String TASK_REPO = "repo";
	public static final String TASK_I_REPO_CUSTOM_DSL = "i_repo_custom_dsl";
	public static final String TASK_IMPL_REPO_CUSTOM_DSL = "impl_repo_custom_dsl";
	public static final String TASK_I_SERVICES = "iservices";
	public static final String TASK_SERVICES = "services";
	public static final String TASK_SERVICES_RELATIONSHIP = "services_relationship";
	public static final String TASK_CONTROLLER = "controller";

	public static void main(String[] args) {

		/**
		 * PARAM
		 */
		String urlFileSQL = "D:\\CODE_\\hibernate-generate-from-file-sql\\src\\main\\resources\\file-sql-export-from-database.sql"; // current
																																	// gen
		/**
		 * PREPROCESSING NOTE: current support only file sql export from mysql. you can
		 * see example file in 'resources'. If you want change please read code :D
		 */
		List<String> lines = PreprocessFileUtil.preprocessFileSQL(urlFileSQL);
		// PLEASE SEE FILE resource/file-sql-after-preprocessing.txt
		// for (String line : lines) {
		// 	System.out.println(line);
		// }
		List<EntityStruct> entites = ModelGenerator.generateFromMySql(lines);																															// only
																																	// mysql
																																	// export
		// folder export
		String urlFolder = "D:\\CODE_\\sakila-spring-boot\\src\\main\\java\\com\\example\\demo";
		String basePackage = "com.example.demo";

		/**
		 * LIST TASK
		 */
		List<String> taskExports = new ArrayList<>();

		// taskExports.add(TASK_ENTITY);
//		taskExports.add(TASK_ENTITY_RELATIONSHIP);

		// taskExports.add(TASK_DTO);

		taskExports.add(TASK_REPO);
		taskExports.add(TASK_I_REPO_CUSTOM_DSL);
		taskExports.add(TASK_IMPL_REPO_CUSTOM_DSL);
//		taskExports.add(TASK_I_SERVICES);

		// taskExports.add(TASK_SERVICES);
//		taskExports.add(TASK_SERVICES_RELATIONSHIP);

//		taskExports.add(TASK_CONTROLLER);

		// Optional config. Normal, it depend on taskExports
		OptionalConfig config = new OptionalConfig(taskExports);
		config.setHaveTaskRelationShip(true); // if not exist

		/**
		 * CREATE FILE FROM TASK
		 */
		IContentGenerator generator = null;
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
				case TASK_ENTITY_RELATIONSHIP:
					generator = new ContentEntityRelationshipGenerator();
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
				case TASK_I_REPO_CUSTOM_DSL:
					generator = new ContentRepoCustomGenerator();
					urlFolderContent += "\\" + "repo\\custom";
					prefixNameFile = "";
					suffixNameFile = "RepoCustom";
					break;
				case TASK_IMPL_REPO_CUSTOM_DSL:
					generator = new ContentRepoCustomImplGenerator();
					urlFolderContent += "\\" + "repo\\custom\\impl";
					suffixNameFile = "RepoCustomImpl";
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
				case TASK_SERVICES_RELATIONSHIP:
					generator = new ContentServiceRelationshipGenerator();
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
			System.out.println("----------------------------------" + generator.getClass().getSimpleName() + "------------------------------------------------");
			for (EntityStruct entity : entites) {
				// System.out.println(entity.toString());
				String content = generator.gen(basePackage, entity, config);
				if (content == null || content.equals("")) {
					System.out.println("Entity name = " + entity.getNameClass() + ", Generator class = "
							+ generator.getClass().getSimpleName() + "  -- Empty content");
					continue;
				}
				String nameFile = prefixNameFile + entity.getNameClass() + suffixNameFile + ".java";
				System.out.println("File name: " + nameFile);
				ExportFileUtil.exportDataToFolder(urlFolderContent, nameFile, content);
				System.out.println(content);
			}
			// 3. reset generator
			generator = null;
			prefixNameFile = "";
			suffixNameFile = "";
		}

		System.out.println("============ OUPUT FOLDER ============");
		System.out.println(urlFolder);
	}
}
