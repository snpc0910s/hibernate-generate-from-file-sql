package snpc.generate.entity.hibernate.model;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.util.StringUtil;

public class EntityStruct {
	private int no;
	private String nameTable;
	private String nameClass;
	private boolean singleKey = true;

	private List<String> fullData = new ArrayList<>();
	private List<PropertiesStruct> properties = new ArrayList<>();
	private List<String> constraints = new ArrayList<>();
	private List<PropertiesStruct> extendProperties = new ArrayList<>();

	public void appendProperties(PropertiesStruct property) {
		this.properties.add(property);
	}

	public boolean existKey() {
		for (PropertiesStruct property : properties)
			if (property.isKey())
				return true;
		return false;
	}

	public PropertiesStruct findKeyIfSingleKey() {
		if (this.singleKey == false)
			return null;
		for (PropertiesStruct property : properties) {
			if (property.isKey())
				return property;
		}
		return null;
	}

	public String getNameClassProperty() {
		return StringUtil.lowerFirstLetter(this.nameClass);
	}

	public String getNameTable() {
		return nameTable;
	}

	public void setNameTable(String nameTable) {
		this.nameTable = nameTable;
	}

	public String getNameClass() {
		return nameClass;
	}

	public void setNameClass(String nameClass) {
		this.nameClass = nameClass;
	}

	public boolean isSingleKey() {
		return singleKey;
	}

	public void setSingleKey(boolean singleKey) {
		this.singleKey = singleKey;
	}

	public List<String> getFullData() {
		return fullData;
	}

	public void setFullData(List<String> fullData) {
		this.fullData = fullData;
	}

	public List<PropertiesStruct> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertiesStruct> properties) {
		this.properties = properties;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public List<String> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<String> constraints) {
		this.constraints = constraints;
	}

	public List<PropertiesStruct> getExtendProperties() {
		return extendProperties;
	}

	public void setExtendProperties(List<PropertiesStruct> extendProperties) {
		this.extendProperties = extendProperties;
	}

	public boolean existNamePropertiesEqualNameTable() {
		for(PropertiesStruct pro : this.properties) {
			if(pro.getNameColumn().equals(this.nameTable)) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("======================= No: " + this.no + " ==================================\n");
		buffer.append("Table name: " + this.nameTable + "\n");
		buffer.append("Class name: " + this.nameClass + "\n");
		buffer.append("Single key: " + this.singleKey + "\n");
		buffer.append("Properties:\n");
//		for (String line : this.fullData) {
//			buffer.append(line + "\n");
//		}
		for (PropertiesStruct pro : this.properties) {
			buffer.append(pro.toString());
		}
		buffer.append("\nConstraint:\n");
		for(String constraint : this.constraints) {
			buffer.append(constraint + "\n");
		}
		buffer.append("\nData sql:\n");
		for (String lineSql : this.fullData) {
			buffer.append("    "+lineSql + "\n");
		}
		buffer.append("\nProperties Extend (support entity relationship):\n");
		for (PropertiesStruct pro : this.extendProperties) {
			buffer.append(pro.toString());
		}
		
		return buffer.toString();
	}

}
