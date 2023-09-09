package snpc.generate.entity.hibernate.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import snpc.generate.entity.hibernate.mapper.MysqlTypeMapper;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;
import snpc.generate.entity.hibernate.util.StringUtil;

public class ModelGenerator {
	private static String S_REGEX_FOREIGN_KEY =  "^CONSTRAINT (?<constranitname>\\w+) FOREIGN KEY \\((?<columnname>\\w+)\\) REFERENCES (?<tablerefer>\\w+) \\((?<columnrerefer>\\w+)\\)( .*)?";
	private static Pattern CONSTRAINT_REGEX = Pattern.compile(S_REGEX_FOREIGN_KEY, Pattern.CASE_INSENSITIVE);
	
	
	public static List<EntityStruct> generateFromMySql(List<String> lines) {
		Map<String, EntityStruct> mapResult = new HashMap<>();
		Map<String, List<String>> mapConstraint = new HashMap<>();
		int no = 1;
		String currentTable = "";
		// first line must "CREATE TABLE"
		for (String line : lines) {
			// 1. create new table
			if (line.startsWith("CREATE TABLE")) {
				String[] splitLine = line.split("\\s");
				currentTable = splitLine[2]; // get current name table
				EntityStruct newStruct = new EntityStruct();
				newStruct.setNameTable(currentTable);
				newStruct.setNameClass(StringUtil.nameToNameClass(currentTable));
				newStruct.setNo(no);
				mapResult.put(currentTable, newStruct);
				// increament number
				no++;
				continue;
			}
			// 2. keys
			if (line.startsWith("PRIMARY KEY")) {
				EntityStruct entity = mapResult.get(currentTable);
				line = line.substring(0, line.length() - 1);
				String[] splitLine = line.split("\\s");
				String lsKey = splitLine[2]; // (film_id) or (actor_id,film_id),...
				lsKey = lsKey.replaceAll("\\(|\\)", "");
				String[] keys = lsKey.split(",");
				if (keys.length != 1) { // 2 or more
					entity.setSingleKey(false);
				}
				// set key for property
				List<PropertiesStruct> properties = entity.getProperties();
				for (PropertiesStruct property : properties) {
					for (String key : keys) {
						if (property.getNameColumn().equals(key))
							property.setKey(true);
					}
				}
				// add full line
				entity.getFullData().add(line);
				continue;
			}
			// 3. constraint
			if (line.startsWith("CONSTRAINT")) {
				// add all stack constraint
				if(mapConstraint.containsKey(currentTable)) {
					mapConstraint.get(currentTable).add(line);
				}else {
					List<String> newListContraint = new ArrayList<>();
					newListContraint.add(line);
					mapConstraint.put(currentTable,newListContraint);
				}
				mapResult.get(currentTable).getFullData().add(line);
				continue;
			}

			// 4. columns
			EntityStruct entity = mapResult.get(currentTable);
			// add data line
			entity.getFullData().add(line);
			// create property
			line = line.substring(0, line.length() - 1);
			MysqlTypeMapper mysqlMapperType = new MysqlTypeMapper(); // mapper support mysql
			entity.appendProperties(PropertiesStruct.createFromLine(line, mysqlMapperType));
		}
		// 5. modify constraint
		for (Map.Entry<String, List<String>> entry : mapConstraint.entrySet()) {
			String nameTable = entry.getKey();
			List<String> constraints = entry.getValue();

			for (String constraint : constraints) {
				mapResult.get(nameTable).getConstraints().add(constraint);
				// set constraint
				// 5.1 foreign key
				if(constraint.contains("FOREIGN KEY")) {
					Matcher matcher = CONSTRAINT_REGEX.matcher(constraint);
					if(matcher.matches()) {
						String nameConstraint = matcher.group("constranitname");
						String columnName = matcher.group("columnname");
						String tableRefer = matcher.group("tablerefer");
						String columnRefer = matcher.group("columnrerefer");
						EntityStruct entity = mapResult.get(nameTable);
						for(PropertiesStruct property: entity.getProperties()) {
							if(property.getNameColumn().equals(columnName)) {
								property.setRelationshipName(nameConstraint);
								property.setHaveRelationShip(true);
								property.setRelationshipWithTable(tableRefer);
								property.setRelationshipWithTableColumn(columnRefer);
							}
						}
						// update one to many to parent table
						String nameClassChild= StringUtil.nameToNameClass(nameTable);
						String namePropertyChild = StringUtil.nameToNameProperties("lst" + nameClassChild);
						// if exist 1 one to many to parent table/ It's mean if exist more columns have reference to 1 table -> increment name properties. ex: lstLanguage -> lstLanguage1
						int count = 0;
						for(PropertiesStruct tPro : mapResult.get(tableRefer).getExtendProperties()) {
							if(tPro.getNameProperty().equals(namePropertyChild)) {
								count++;
							}
						}
						if(count != 0) 
							namePropertyChild = namePropertyChild + count;
						PropertiesStruct propertyExtend = new PropertiesStruct();
						propertyExtend.setFullStringLine("From Table: "+ nameTable + " -- " + constraint);
						propertyExtend.setNameColumn(columnName);
						propertyExtend.setTypeProperty("List<"+nameClassChild+">");
						propertyExtend.setNameProperty(namePropertyChild);
						mapResult.get(tableRefer).getExtendProperties().add(propertyExtend);
					}
				}
			}

			
			
		}
		
		// sort value
		List<EntityStruct> result = new ArrayList<>(mapResult.values());
		Collections.sort(result, new Comparator<EntityStruct>() {
			@Override
			public int compare(EntityStruct o1, EntityStruct o2) {
				return o1.getNo() - o2.getNo();
			}
		});
		return result;
	}
}
