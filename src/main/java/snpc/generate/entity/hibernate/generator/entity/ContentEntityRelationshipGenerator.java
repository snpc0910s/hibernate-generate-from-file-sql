package snpc.generate.entity.hibernate.generator.entity;

import java.util.ArrayList;
import java.util.List;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.generator.OptionalConfig;
import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;
import snpc.generate.entity.hibernate.util.StringUtil;

public class ContentEntityRelationshipGenerator implements IContentGenerator{

    
	/*
		package com.example.demo.entity;
		import java.util.Date;
		import java.util.List;
		import java.util.ArrayList;
		import javax.persistence.Column;
		import javax.persistence.Entity;
		import javax.persistence.Table;
		import javax.persistence.OneToMany;
		import javax.persistence.JoinColumn;
		import javax.persistence.ManyToOne;
		import javax.persistence.GeneratedValue;
		import javax.persistence.GenerationType;
		import javax.persistence.Id;

		@Entity
		@Table(name = "address")
		public class Address {

			@Id
			@GeneratedValue(strategy = GenerationType.IDENTITY)
			@Column(name = "address_id")
			private Integer addressId;

			@Column(name = "address")
			private String address;

			@Column(name = "address2")
			private String address2;

			@Column(name = "district")
			private String district;


			@ManyToOne(fetch = FetchType.LAZY)
			@JoinColumn(name = "city_id")
			private City city;

			@Column(name = "postal_code")
			private String postalCode;

			@Column(name = "phone")
			private String phone;

			@Column(name = "location")
			private String location;

			@Column(name = "last_update")
			private Date lastUpdate;

			@OneToMany(mappedBy = "address")
			private List<Staff> lstStaff = new ArrayList<>();

			@OneToMany(mappedBy = "address")
			private List<Store> lstStore = new ArrayList<>();

			@OneToMany(mappedBy = "address")
			private List<Customer> lstCustomer = new ArrayList<>();

			// getter and setter for @OneToMany
			public List<Staff> getLstStaff() {
				return lstStaff;
			}
			public void setLstStaff(List<Staff> lstStaff) {
				this.lstStaff = lstStaff;
			}
			public List<Store> getLstStore() {
				return lstStore;
			}
			public void setLstStore(List<Store> lstStore) {
				this.lstStore = lstStore;
			}
			public List<Customer> getLstCustomer() {
				return lstCustomer;
			}
			public void setLstCustomer(List<Customer> lstCustomer) {
				this.lstCustomer = lstCustomer;
			}

			// getter and setter
			... getter and setter
		}
	*/
	/*
	 * Embedded ID -> Many to many *****************************
			package com.example.demo.entity;
			import java.util.Date;
			import java.util.List;
			import java.util.ArrayList;
			import javax.persistence.Column;
			import javax.persistence.Entity;
			import javax.persistence.Table;
			import javax.persistence.OneToMany;
			import javax.persistence.JoinColumn;
			import javax.persistence.ManyToOne;
			import java.io.Serializable;
			import javax.persistence.Embeddable;
			import javax.persistence.EmbeddedId;
			import javax.persistence.MapsId;

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

				@ManyToOne(fetch = FetchType.LAZY)
				@MapsId("filmId")
				@JoinColumn(name = "film_id")
				private Film film;


				@ManyToOne(fetch = FetchType.LAZY)
				@MapsId("categoryId")
				@JoinColumn(name = "category_id")
				private Category category;

				@Column(name = "last_update")
				private Date lastUpdate;

				// getter and setter
				public Film getFilm() {
					return film;
				}
				public void setFilm(Film film) {
					this.film = film;
				}
				public Category getCategory() {
					return category;
				}
				public void setCategory(Category category) {
					this.category = category;
				}
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
	public String gen(String basePackage, EntityStruct entity , OptionalConfig config) {
		StringBuffer buffer = new StringBuffer();

		// 1. init header
		buffer.append("package " + basePackage + ".entity;\n");
		buffer.append("");
		buffer.append("import java.util.Date;\n");
		buffer.append("import java.util.List;\n");
		buffer.append("import java.util.ArrayList;\n");
		buffer.append("import javax.persistence.Column;\n");
		buffer.append("import javax.persistence.Entity;\n");
		buffer.append("import javax.persistence.FetchType;\n");
		buffer.append("import javax.persistence.Table;\n");
		buffer.append("import javax.persistence.OneToMany;\n");
		buffer.append("import javax.persistence.JoinColumn;\n");
		buffer.append("import javax.persistence.ManyToOne;\n");
		if(entity.isSingleKey() == false) {
			buffer.append("import java.io.Serializable;\n");
			buffer.append("import javax.persistence.Embeddable;\n");
			buffer.append("import javax.persistence.EmbeddedId;\n");
			buffer.append("import javax.persistence.MapsId;\n");
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
				// CASE 1:  PROPERTY IS A KEY
				buffer.append("    @Id\n");
				if (property.getFullStringLine().contains("AUTO_INCREMENT")) {
					buffer.append("    @GeneratedValue(strategy = GenerationType.IDENTITY)\n");
				}
				buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
				buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
			} else if (property.isKey() == true && entity.isSingleKey() == false) {
				// CASE 2: PROPERTY IS A COMBINE KEY (> 2 KEY)
				if (added == false) {
					String embedded = this.createEmbeddedFromEntity(entity);
					buffer.append(embedded);
					buffer.append("    @EmbeddedId\n");
					buffer.append("    private " + entity.getNameClass() + "Id id;\n");
				}
				added = true; // block add twice @EmbeddedId
				// add @Many to one
				buffer.append("\n    @ManyToOne(fetch = FetchType.LAZY)\n");
				buffer.append("    @MapsId(\""+property.getNameProperty()+"\")\n");
				buffer.append("    @JoinColumn(name = \""+property.getNameColumn()+"\")\n");
				buffer.append("    private " + StringUtil.nameToNameClass(property.getRelationshipWithTable()) + " " + StringUtil.nameToNamePropertiesButRemoveEndId(property.getNameProperty()) + ";\n");
                
			} else {
				// CASE 3: NORMAL PROPERTIES
                if(property.isHaveRelationShip() == false ) {
                    buffer.append("    @Column(name = \"" + property.getNameColumn() + "\")\n");
                    buffer.append("    private " + property.getTypeProperty() + " " + property.getNameProperty() + ";\n");
                }else {
                    // property.isHaveRelationShip() == true
                    // step 1: add many to one this entity struct
                    buffer.append("\n    @ManyToOne(fetch = FetchType.LAZY)\n");
                    buffer.append("    @JoinColumn(name = \""+property.getNameColumn()+"\")\n");
                    buffer.append("    private " + StringUtil.nameToNameClass(property.getRelationshipWithTable()) + " " + StringUtil.nameToNamePropertiesButRemoveEndId(property.getNameProperty()) + ";\n");
                }
				
			}
		}
		// extend properties support for @OneToMany
		for (PropertiesStruct property : entity.getExtendProperties()) {
			buffer.append("\n    @OneToMany(mappedBy = \""+StringUtil.nameToNamePropertiesButRemoveEndId(property.getNameColumn())+"\")\n");
			buffer.append("    private "+property.getTypeProperty()+" "+property.getNameProperty()+" = new ArrayList<>();\n");
		}
		// 3. init getter setter
		if(entity.getExtendProperties().size() > 0 ) {
			buffer.append("\n    // getter and setter for @OneToMany\n");
			// for extend propteries support @OneToMany
			for (PropertiesStruct property : entity.getExtendProperties()) {
				buffer.append(property.genGetterAndSetter(1));	
			}
		}
		// for normal properties
		buffer.append("\n    // getter and setter\n");
		for (PropertiesStruct property : entity.getProperties()) {
			if(entity.isSingleKey() == true) {
                if(property.isHaveRelationShip() == false) {
                    buffer.append(property.genGetterAndSetter(1));				
                }else if(property.isHaveRelationShip() == true) {
                    // properties have relationship
                    buffer.append(property.genGetterAndSetterCaseHaveRelationship(1));
                }
			}else {
				// have more > 2 key
				if(property.isKey() == false && property.isHaveRelationShip() == false) {
                    // normal properties don't have relationship
					buffer.append(property.genGetterAndSetter(1));
				} else if(property.isKey() == false && property.isHaveRelationShip() == true) { 
                    // properties have relationship
                    buffer.append(property.genGetterAndSetterCaseHaveRelationship(1));
                } else if(property.isKey() == true && property.isHaveRelationShip() == true) {
					buffer.append(property.genGetterAndSetterCaseHaveRelationship(1));
				}
			}
		}
		// for ID intermediate table
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
