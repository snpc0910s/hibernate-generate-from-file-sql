package snpc.generate.entity.hibernate.generator;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentEntityGenerator implements IContentGenerator {
	/*
	package com.example.demo.entity;
	import java.util.Date;
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Table;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;

	@Entity
	@Table(name = "actor")
	public class Actor {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "actor_id")
	    private Integer actorId;

	    @Column(name = "first_name")
	    private String firstName;

	    @Column(name = "last_name")
	    private String lastName;

	    @Column(name = "last_update")
	    private Date lastUpdate;

		// getter and setter
	    public Integer getActorId() {
	        return actorId;
	    }
	    public void setActorId(Integer actorId) {
	        this.actorId = actorId;
	    }
	    public String getFirstName() {
	        return firstName;
	    }
	    public void setFirstName(String firstName) {
	        this.firstName = firstName;
	    }
	    public String getLastName() {
	        return lastName;
	    }
	    public void setLastName(String lastName) {
	        this.lastName = lastName;
	    }
	    public Date getLastUpdate() {
	        return lastUpdate;
	    }
	    public void setLastUpdate(Date lastUpdate) {
	        this.lastUpdate = lastUpdate;
	    }
	}
	*/
	/*
	 * Embedded ID -> Many to many *****************************
	package com.example.demo.entity;
	import java.util.Date;
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.Table;
	import java.io.Serializable;
	import javax.persistence.Embeddable;
	import javax.persistence.EmbeddedId;

	@Entity
	@Table(name = "film_category")
	public class FilmCategory {

	    // static class
	    @Embeddable
	    public static class FilmCategoryId implements Serializable {
	        @Column(name = "film_id")
	        private Integer filmId;
	        @Column(name = "category_id")
	        private Integer categoryId;


	        public FilmCategoryId(){}

	        public FilmCategoryId(Integer filmId,Integer categoryId) {
	            this.filmId = filmId;
	            this.categoryId = categoryId;
	        }
	        

			// getter and setter 2 key
	        public Integer getFilmId() {
	            return filmId;
	        }
	        public void setFilmId(Integer filmId) {
	            this.filmId = filmId;
	        }
	        public Integer getCategoryId() {
	            return categoryId;
	        }
	        public void setCategoryId(Integer categoryId) {
	            this.categoryId = categoryId;
	        }


	        @Override
	        public int hashCode() {
	            final int prime = 31;
	            int result = 1;
	            result = prime * result + ((filmId == null) ? 0 : filmId.hashCode());
	            result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
	            return result;
	        }

	        @Override
	        public boolean equals(Object obj) {
	            if (this == obj)
	                return true;
	            if (obj == null)
	                return false;
	            if (getClass() != obj.getClass())
	                return false;
	            FilmCategoryId other = (FilmCategoryId) obj;
	            if (filmId == null) {
	                if (other.filmId != null)
	                    return false;
	            } else if (!filmId.equals(other.filmId))
	                return false;
	            if (categoryId == null) {
	                if (other.categoryId != null)
	                    return false;
	            } else if (!categoryId.equals(other.categoryId))
	                return false;
	            return true;
	        }
	    }
	    @EmbeddedId
	    private FilmCategoryId id;


	    @Column(name = "last_update")
	    private Date lastUpdate;

		// getter and setter
	    public Date getLastUpdate() {
	        return lastUpdate;
	    }
	    public void setLastUpdate(Date lastUpdate) {
	        this.lastUpdate = lastUpdate;
	    }
	    public FilmCategoryId getId() {
	        return id;
	    }

	    public void setId(FilmCategoryId id) {
	        this.id = id;
	    }
	}
	*/
	@Override
	public String gen(String basePackage, EntityStruct entity) {
		StringBuffer buffer = new StringBuffer();

		// 1. init header
		buffer.append("package " + basePackage + ".entity;\n");
		buffer.append("");
		buffer.append("import java.util.Date;\n");
		buffer.append("import javax.persistence.Column;\n");
		buffer.append("import javax.persistence.Entity;\n");
		buffer.append("import javax.persistence.Table;\n");
		if(entity.isSingleKey() == false) {
			buffer.append("import java.io.Serializable;\n");
			buffer.append("import javax.persistence.Embeddable;\n");
			buffer.append("import javax.persistence.EmbeddedId;\n");
		}else {
			buffer.append("import javax.persistence.GeneratedValue;\n");
			buffer.append("import javax.persistence.GenerationType;\n");
			buffer.append("import javax.persistence.Id;\n");
		}
		buffer.append("\n");
		buffer.append("@Entity\n");
		buffer.append("@Table(name = \"" + entity.getNameTable() + "\")\n");
		buffer.append("public class " + entity.getNameClass() + " {\n");
		buffer.append("");
		// 2. init properties
		boolean added = false;
		for (PropertiesStruct property : entity.getProperties()) {
			buffer.append("\n");
			if (property.isKey() == true && entity.isSingleKey() == true) {
				// generate only 1 id
				buffer.append("    @Id\n");
				if (property.getFullStringLine().contains("AUTO_INCREMENT")) {
					buffer.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
				}
				buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
				buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			} else if (property.isKey() == true && entity.isSingleKey() == false) {
				// generate 2 or more id
				if (added == false) {
					String embedded = this.createEmbeddedFromEntity(entity);
					buffer.append(embedded);
					buffer.append("    @EmbeddedId\n");
					buffer.append("    private " + entity.getNameClass() + "Id id;\n");

				}
				added = true;
			} else {
				// normal property
				buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
				buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			}

		}
		// 3. init getter setter
		buffer.append("\n");
		buffer.append("	// getter and setter\n");
		for (PropertiesStruct property : entity.getProperties()) {
			if(entity.isSingleKey() == true) {
				buffer.append(property.genGetterAndSetter(1));				
			}else {
				if(property.isKey() == false) {
					buffer.append(property.genGetterAndSetter(1));
				}
			}
		}
		if (entity.isSingleKey() == false) {
			buffer.append("    public " + entity.getNameClass() + "Id getId() {\n");
			buffer.append("        return id;\n");
			buffer.append("    }\n");
			buffer.append("\n");
			buffer.append("    public void setId(" + entity.getNameClass() + "Id id) {\n");
			buffer.append("        this.id = id;\n");
			buffer.append("    }\n");
		}
		// 4. end and return
		buffer.append("}");
		return buffer.toString();
	}

	private String createEmbeddedFromEntity(EntityStruct entity) {
		// get only primary key
		List<PropertiesStruct> proIds = new ArrayList<>();
		for (PropertiesStruct pro : entity.getProperties()) {
			if (pro.isKey() == true)
				proIds.add(pro);
		}
		List<String> constructorFullParam = new ArrayList<>();
		for (PropertiesStruct property : proIds) {
			constructorFullParam.add(property.getTypeProperty() + " " + property.getNameProperty());
		}

		// process
		StringBuffer buffer = new StringBuffer();

		buffer.append("    // static class\n");
		buffer.append("    @Embeddable\n");
		buffer.append("    public static class " + entity.getNameClass() + "Id implements Serializable {\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("        @Column(name = \"" + property.getNameColumn() + "\")\n");
			buffer.append("        private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("        public " + entity.getNameClass() + "Id(){}\n");
		buffer.append("\n");
		buffer.append(
				"        public " + entity.getNameClass() + "Id(" + String.join(",", constructorFullParam) + ") {\n");
		for (PropertiesStruct property : proIds) {
			buffer.append(
					"            this." + property.getNameProperty() + " = " + property.getNameProperty() + ";\n");
		}
		buffer.append("        }\n");
		buffer.append("        \n");
		buffer.append("\n");
		buffer.append("		// getter and setter 2 key\n");
		for (PropertiesStruct property : proIds) {
			buffer.append(property.genGetterAndSetter(2));
		}
		buffer.append("\n");
		buffer.append("\n");
		buffer.append("        @Override\n");
		buffer.append("        public int hashCode() {\n");
		buffer.append("            final int prime = 31;\n");
		buffer.append("            int result = 1;\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("            result = prime * result + ((" + property.getNameProperty() + " == null) ? 0 : "
					+ property.getNameProperty() + ".hashCode());\n");
		}
		buffer.append("            return result;\n");
		buffer.append("        }\n");
		buffer.append("\n");
		buffer.append("        @Override\n");
		buffer.append("        public boolean equals(Object obj) {\n");
		buffer.append("            if (this == obj)\n");
		buffer.append("                return true;\n");
		buffer.append("            if (obj == null)\n");
		buffer.append("                return false;\n");
		buffer.append("            if (getClass() != obj.getClass())\n");
		buffer.append("                return false;\n");
		buffer.append("            " + entity.getNameClass() + "Id other = (" + entity.getNameClass() + "Id) obj;\n");
		for (PropertiesStruct property : proIds) {
			buffer.append("            if (" + property.getNameProperty() + " == null) {\n");
			buffer.append("                if (other." + property.getNameProperty() + " != null)\n");
			buffer.append("                    return false;\n");
			buffer.append("            } else if (!" + property.getNameProperty() + ".equals(other."
					+ property.getNameProperty() + "))\n");
			buffer.append("                return false;\n");
		}
		buffer.append("            return true;\n");
		buffer.append("        }\n");
		buffer.append("    }\n");

		// append ID to parent class

		return buffer.toString();
	}
}
