package snpc.generate.entity.hibernate.generator;

import snpc.generate.entity.hibernate.model.EntityStruct;
import snpc.generate.entity.hibernate.model.PropertiesStruct;

public class ContentServiceGenerator implements IContentGenerator {
	/*
	package com.example.demo.services;

	import java.util.Date;
	import java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

	import com.example.demo.entity.Store;
	import com.example.demo.repo.StoreRepo;

	@Service
	public class StoreServiceImpl implements IStoreService {

	    @Autowired
	    private StoreRepo storeRepo;

	    @Transactional(readOnly = true)
	    @Override
	    public List<Store> findAll() {
	        return storeRepo.findAll();
	    }

	    @Transactional(readOnly = true)
	    @Override
	    public Optional<Store> findById(Integer id) {
	        return storeRepo.findById(id);
	    }

	    @Transactional(readOnly = true)
	    @Override
	    public boolean exist(Integer id) {
	        return storeRepo.existsById(id);
	    }

	    @Transactional(readOnly = false)
	    @Override
	    public Optional<Store> insert(Store saveStore) {
	        Store store = new Store();
	        store.setStoreId(null);
	        store.setManagerStaffId(saveStore.getManagerStaffId());
	        store.setAddressId(saveStore.getAddressId());
	        store.setLastUpdate(saveStore.getLastUpdate());
	        Store o = storeRepo.save(store);
	        return storeRepo.findById(o.getStoreId());
	    }

	    @Transactional(readOnly = false)
	    @Override
	    public Optional<Store> update(Integer StoreId, Store saveStore) {
	        saveStore.setStoreId(storeId);
	        Optional<Store> storeOptional = storeRepo.findById(saveStore.getStoreId());
	        if (storeOptional.isPresent()) {
	            Store store = storeOptional.get();
	            store.setManagerStaffId(saveStore.getManagerStaffId());
	            store.setAddressId(saveStore.getAddressId());
	            store.setLastUpdate(saveStore.getLastUpdate());
	            Store o = storeRepo.save(store);
	            return storeRepo.findById(o.getStoreId());
	        }
	        return Optional.empty();
	    }
	}
	*/
	@Override
	public String gen(String basePackage, EntityStruct entity) {
		/**
		 * NOT SUPPORT SERVICE FOR TABLE 2 OR MORE KEY
		 */
		if(entity.isSingleKey() == false) 
			return "";
		String NAME_CLASS = entity.getNameClass();
		String NAME_CLASS_PROPERTIES = entity.getNameClassProperty();
		
		StringBuffer buffer = new StringBuffer();
		// header
		buffer.append("package "+basePackage+".services.impl;\n");
		buffer.append("\n");
		buffer.append("import java.util.List;\n");
		buffer.append("import java.util.Optional;\n");
		buffer.append("\n");
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		buffer.append("import org.springframework.stereotype.Service;\n");
		buffer.append("import org.springframework.transaction.annotation.Transactional;\n");
		buffer.append("\n");
		buffer.append("import "+basePackage+".entity."+NAME_CLASS+";\n");
		buffer.append("import "+basePackage+".repo."+NAME_CLASS+"Repo;\n");
		buffer.append("import "+basePackage+".services.I"+NAME_CLASS+"Service;\n");
		buffer.append("\n");
		buffer.append("@Service\n");
		buffer.append("public class "+NAME_CLASS+"ServiceImpl implements I"+NAME_CLASS+"Service {\n");
		buffer.append("\n");
		// declare repo
		buffer.append("    @Autowired\n");
		buffer.append("    private "+NAME_CLASS+"Repo "+NAME_CLASS_PROPERTIES+"Repo;\n");
		buffer.append("\n");
		// main function: find all
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public List<"+NAME_CLASS+"> findAll() {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findAll();\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: find by id
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> findById("+entity.findKeyIfSingleKey().getTypeProperty()+" id) {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findById(id);\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: exist
		buffer.append("    @Transactional(readOnly = true)\n");
		buffer.append("    @Override\n");
		buffer.append("    public boolean exist(Integer id) {\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.existsById(id);\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: insert
		buffer.append("    @Transactional(readOnly = false)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> insert("+NAME_CLASS+" save"+NAME_CLASS+") {\n");
		buffer.append("        "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = new "+NAME_CLASS+"();\n");
		for(PropertiesStruct property: entity.getProperties()) {
			if(property.isKey()) {
				buffer.append("        "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(null);\n");
			}else {
				buffer.append("        "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
			}
		}
		buffer.append("        "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
		buffer.append("        return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
		buffer.append("    }\n");
		buffer.append("\n");
		// main function: update
		buffer.append("    @Transactional(readOnly = false)\n");
		buffer.append("    @Override\n");
		buffer.append("    public Optional<"+NAME_CLASS+"> update("+entity.findKeyIfSingleKey().getTypeProperty()+" "+NAME_CLASS_PROPERTIES+"Id, "+NAME_CLASS+" save"+NAME_CLASS+") {\n");
		buffer.append("        save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodSet()+"("+NAME_CLASS_PROPERTIES+"Id);\n");
		buffer.append("        Optional<"+NAME_CLASS+"> "+NAME_CLASS_PROPERTIES+"Optional = "+NAME_CLASS_PROPERTIES+"Repo.findById(save"+NAME_CLASS+"."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
		buffer.append("        if ("+NAME_CLASS_PROPERTIES+"Optional.isPresent()) {\n");
		buffer.append("            "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+" = "+NAME_CLASS_PROPERTIES+"Optional.get();\n");
		for(PropertiesStruct property: entity.getProperties()) {
			if(property.isKey()) {
				continue;
			}else {
				buffer.append("            "+NAME_CLASS_PROPERTIES+"."+property.getNameMethodSet()+"(save"+NAME_CLASS+"."+property.getNameMethodGet()+"());\n");
			}
		}
		buffer.append("            "+NAME_CLASS+" o = "+NAME_CLASS_PROPERTIES+"Repo.save("+NAME_CLASS_PROPERTIES+");\n");
		buffer.append("            return "+NAME_CLASS_PROPERTIES+"Repo.findById(o."+entity.findKeyIfSingleKey().getNameMethodGet()+"());\n");
		buffer.append("        }\n");
		buffer.append("        return Optional.empty();\n");
		buffer.append("    }\n");
		buffer.append("}\n");
		buffer.append("\n");
		return buffer.toString();
	}

}
