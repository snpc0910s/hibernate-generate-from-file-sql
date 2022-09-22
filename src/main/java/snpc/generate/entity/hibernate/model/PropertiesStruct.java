package snpc.generate.entity.hibernate.model;

import org.apache.commons.lang3.StringUtils;

import snpc.generate.entity.hibernate.mapper.IMapperType;
import snpc.generate.entity.hibernate.mapper.MysqlTypeMapper;
import snpc.generate.entity.hibernate.util.StringUtil;

public class PropertiesStruct {
	private boolean isKey = false;
	private String nameColumn;
	private String nameProperty;
	private String columnType;
	private String typeProperty;
	private String lengthTypel;
	private boolean haveRelationShip = false;
	private String relationshipName;
	private String relationshipWithTable;
	private String relationshipWithTableColumn;
	private String fullStringLine;

	public static PropertiesStruct createFromLine(String line, IMapperType mapperType) {
		PropertiesStruct struct = new PropertiesStruct();
		String[] elLine = line.split(" ");
		String nameColumn = elLine[0];
		String nameProperty = StringUtil.nameToNameProperties(nameColumn);
		String columnType = elLine[1];// smallint, varchar(45), date,...
		String typeProperty = "";
		// dependent type db mapper
		if (mapperType instanceof MysqlTypeMapper) {
			if (columnType.contains("(")) {
				// have length: ex: varchar(45), decimal(4,2)
				String tempType = columnType.replaceAll("\\(", " ").replaceAll("\\)", ""); // varchar 45, decimal 4,2
				typeProperty = mapperType.reflexTypeSQLToTypeStringTypeJava(tempType.split(" ")[0], true,
						tempType.split(" ")[1]);
			} else {
				// smallint, int, date,....
				typeProperty = mapperType.reflexTypeSQLToTypeStringTypeJava(columnType, false, "");
			}
		} else {
			System.out.println("CURRENT NOT SUPPORT OTHER DB");
			throw new IllegalArgumentException();
		}
		// set value
		struct.setFullStringLine(line);
		struct.setNameColumn(nameColumn);
		struct.setNameProperty(nameProperty);
		struct.setColumnType(columnType);
		struct.setTypeProperty(typeProperty);

		return struct;
	}

	public String genGetterAndSetter(int levelTab) {
		String tab = "    ";
		if(levelTab != 1) {
			for(int i  = 1 ; i < levelTab ; i++)
				tab = tab + "    ";
		}
		StringBuffer buffer = new StringBuffer();
		// get
		if (this.nameProperty == "Boolean") {
			buffer.append(tab + "public " + this.typeProperty + " is"+StringUtils.capitalize(this.nameProperty)+"() {\n");
			buffer.append(tab + "    return " + this.nameProperty + ";\n");
			buffer.append(tab + "}\n");
		} else {
			buffer.append(tab + "public " + this.typeProperty + " get"+StringUtils.capitalize(this.nameProperty)+"() {\n");
			buffer.append(tab + "    return " + this.nameProperty + ";\n");
			buffer.append(tab + "}\n");
		}
		// set
		buffer.append(tab + "public void set" + StringUtils.capitalize(this.nameProperty) + "(" + this.typeProperty + " "+ this.nameProperty + ") {\n");
		buffer.append(tab + "    this." + this.nameProperty + " = " + this.nameProperty + ";\n");
		buffer.append(tab + "}\n");
		return buffer.toString();
	}
	public String getNameMethodSet() {
		return "set"+StringUtils.capitalize(this.nameProperty);
	}
	public String getNameMethodGet() {
		if (this.nameProperty == "Boolean") {
			return "is"+ StringUtils.capitalize(this.nameProperty);
		}
		else {
			return "get"+ StringUtils.capitalize(this.nameProperty);
		}
	}
	public boolean isKey() {
		return isKey;
	}

	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}

	public String getNameColumn() {
		return nameColumn;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public String getNameProperty() {
		return nameProperty;
	}

	public void setNameProperty(String nameProperty) {
		this.nameProperty = nameProperty;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getTypeProperty() {
		return typeProperty;
	}

	public void setTypeProperty(String typeProperty) {
		this.typeProperty = typeProperty;
	}

	public String getLengthTypel() {
		return lengthTypel;
	}

	public void setLengthTypel(String lengthTypel) {
		this.lengthTypel = lengthTypel;
	}

	public boolean isHaveRelationShip() {
		return haveRelationShip;
	}

	public void setHaveRelationShip(boolean haveRelationShip) {
		this.haveRelationShip = haveRelationShip;
	}

	public String getRelationshipWithTable() {
		return relationshipWithTable;
	}

	public void setRelationshipWithTable(String relationshipWithTable) {
		this.relationshipWithTable = relationshipWithTable;
	}

	public String getFullStringLine() {
		return fullStringLine;
	}

	public String getRelationshipWithTableColumn() {
		return relationshipWithTableColumn;
	}

	public void setRelationshipWithTableColumn(String relationshipWithTableColumn) {
		this.relationshipWithTableColumn = relationshipWithTableColumn;
	}

	public void setFullStringLine(String fullStringLine) {
		this.fullStringLine = fullStringLine;
	}

	public String getRelationshipName() {
		return relationshipName;
	}

	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("    --------------" + "\n");
		buffer.append("    Full line: " + this.fullStringLine + "\n");
		buffer.append("    Key: " + this.isKey + "\n");
		buffer.append("    Name property: " + this.nameProperty + "\n");
		buffer.append("        Name column: " + this.nameColumn + "\n");
		buffer.append("    Type property: " + this.typeProperty + "\n");
		buffer.append("        Type column: " + this.columnType + "\n");
		buffer.append("    Have relationship: " + this.haveRelationShip + "\n");
		if (this.haveRelationShip == true) {
			buffer.append("        Foregin: " + this.relationshipName + " : table = " + this.relationshipWithTable
					+ " column = " + this.relationshipWithTableColumn + "\n");
		}
		return buffer.toString();
	}
}
