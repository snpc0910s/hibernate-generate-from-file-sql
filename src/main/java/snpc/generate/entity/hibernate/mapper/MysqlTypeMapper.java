package snpc.generate.entity.hibernate.mapper;

public class MysqlTypeMapper implements IMapperType{

	@Override
	public String reflexTypeSQLToTypeStringTypeJava(String typeSQL, boolean haveLength, String extend) {
		typeSQL = typeSQL.toLowerCase();
		switch (typeSQL) {
		case "char":
			return "String";
		case "varchar":
			return "String";
	   // text
		case "text":
			return "String";
		case "tinytext":
			return "String";
		case "mediumtext":
			return "String";
		case "longtext":
			return "String";
		// enum  set json
		case "enum":
			return "String";
		case "set":
			return "String";
		case "json": 
			return "String";
		case "float":
			return "Double";
		case "decimal": 
			return "Double";
		case "real":
			return "Double";
		case "numeric":
			return "Double";
	    // int
		case "tinyint": {
			if(haveLength == true && extend.equals("1")) { // special case
				return "Boolean";
			}
			return "Integer";
		}
		case "bit": {
			if(haveLength == true && extend.equals("1")) { // special case
				return "Boolean";
			}
			return "byte[]";
		}
		case "int": {
			return "Integer";
		}
		case "smallint":
			return "Integer";
		case "mediumint":
			return "Integer";
		case "integer":
			return "Integer";
		case "bigint":
			return "Long";
		// date
		case "date":
			return "Date";
		case "time":
			return "Date";
		case "datetime":
			return "Date";
		case "timestamp":
			return "Date";
		case "year":
			return "Integer";
		case "month":
			return "Integer";
		case "day":
			return "Integer";
		// geometry
		case "geometry" : 
			return "String";
		// blob
		case "blob":
			return "byte[]";
		case "tinyblob":
			return "byte[]";
		case "mediumblob":
			return "byte[]";
		case "longblob":
			return "byte[]";
		case "binary": 
			return "byte[]";
		// not support
		default:
			break;
		}
		
		return "Unknow";
	}
	
}
