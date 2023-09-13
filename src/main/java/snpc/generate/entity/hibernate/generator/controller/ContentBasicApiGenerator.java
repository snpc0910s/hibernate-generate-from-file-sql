package snpc.generate.entity.hibernate.generator.controller;

import snpc.generate.entity.hibernate.generator.IContentGenerator;
import snpc.generate.entity.hibernate.model.EntityStruct;

public class ContentBasicApiGenerator implements IContentGenerator{
	/*
	package com.example.demo.controller;

	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	import com.example.demo.entity.Country;
	import com.example.demo.services.ICountryService;

	@RestController
	@RequestMapping("/api/country")
	public class CountryController {

	    @Autowired
	    private ICountryService countryService;

	    @GetMapping("")
	    public ResponseEntity<?> findAll() {
	        try {
	            return ResponseEntity.ok(countryService.findAll());
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	        }
	    }

	    @GetMapping("/{countryId}")
	    public ResponseEntity<?> findById(@PathVariable("countryId") Integer countryId) {
	        try {
	            Optional<Country> oCountry = countryService.findById(countryId);
	            if (oCountry.isPresent())
	                return ResponseEntity.ok(oCountry.get());
	            return new ResponseEntity<>("Country is not exist", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	        }
	    }

	    @PostMapping("")
	    public ResponseEntity<?> insert(@RequestBody Country country) {
	        try {
	            Optional<Country> oCountry = countryService.insert(country);
	            if (oCountry.isPresent())
	                return ResponseEntity.ok(oCountry.get());
	            return new ResponseEntity<>("Insert error", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Server error", HttpStatus.BAD_GATEWAY);
	        }
	    }

	    @PutMapping("/{countryId}")
	    public ResponseEntity<?> update(@PathVariable("countryId") Integer countryId, @RequestBody Country country) {
	        try {
	            country.setCountryId(countryId);
	            Optional<Country> oCountry = countryService.update(countryId, country);
	            if (oCountry.isPresent())
	                return ResponseEntity.ok(oCountry.get());
	            return new ResponseEntity<>("Update error", HttpStatus.BAD_REQUEST);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Server error", HttpStatus.BAD_GATEWAY);
	        }
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
		buffer.append("package "+basePackage+".controller;\n");
		buffer.append("\n");
		buffer.append("import java.util.Optional;\n");
		buffer.append("\n");
		buffer.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		buffer.append("import org.springframework.http.HttpStatus;\n");
		buffer.append("import org.springframework.http.ResponseEntity;\n");
		buffer.append("import org.springframework.web.bind.annotation.GetMapping;\n");
		buffer.append("import org.springframework.web.bind.annotation.PathVariable;\n");
		buffer.append("import org.springframework.web.bind.annotation.PostMapping;\n");
		buffer.append("import org.springframework.web.bind.annotation.PutMapping;\n");
		buffer.append("import org.springframework.web.bind.annotation.RequestBody;\n");
		buffer.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		buffer.append("import org.springframework.web.bind.annotation.RestController;\n");
		buffer.append("\n");
		buffer.append("import "+basePackage+".entity."+NAME_CLASS+";\n");
		buffer.append("import "+basePackage+".services.I"+entity.getNameClass()+"Service;\n");
		buffer.append("\n");
		buffer.append("@RestController\n");
		buffer.append("@RequestMapping(\"/api/"+NAME_CLASS_PROPERTIES+"\")\n");
		buffer.append("public class "+NAME_CLASS+"Controller {\n");
		buffer.append("\n");
		buffer.append("    @Autowired\n");
		buffer.append("    private I"+NAME_CLASS+"Service "+NAME_CLASS_PROPERTIES+"Service;\n");
		buffer.append("\n");
		buffer.append("    @GetMapping(\"\")\n");
		buffer.append("    public ResponseEntity<?> findAll() {\n");
		buffer.append("        try {\n");
		buffer.append("            return ResponseEntity.ok("+NAME_CLASS_PROPERTIES+"Service.findAll());\n");
		buffer.append("        } catch (Exception e) {\n");
		buffer.append("            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);\n");
		buffer.append("        }\n");
		buffer.append("    }\n");
		buffer.append("\n");
		buffer.append("    @GetMapping(\"/{"+NAME_CLASS_PROPERTIES+"Id}\")\n");
		buffer.append("    public ResponseEntity<?> findById(@PathVariable(\""+NAME_CLASS_PROPERTIES+"Id\") "+entity.findKeyIfSingleKey().getTypeProperty()+" "+NAME_CLASS_PROPERTIES+"Id) {\n");
		buffer.append("        try {\n");
		buffer.append("            Optional<"+NAME_CLASS+"> o"+NAME_CLASS+" = "+NAME_CLASS_PROPERTIES+"Service.findById("+NAME_CLASS_PROPERTIES+"Id);\n");
		buffer.append("            if (o"+NAME_CLASS+".isPresent())\n");
		buffer.append("                return ResponseEntity.ok(o"+NAME_CLASS+".get());\n");
		buffer.append("            return new ResponseEntity<>(\""+NAME_CLASS+" is not exist\", HttpStatus.BAD_REQUEST);\n");
		buffer.append("        } catch (Exception e) {\n");
		buffer.append("            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);\n");
		buffer.append("        }\n");
		buffer.append("    }\n");
		buffer.append("\n");
		buffer.append("    @PostMapping(\"\")\n");
		buffer.append("    public ResponseEntity<?> insert(@RequestBody "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+") {\n");
		buffer.append("        try {\n");
		buffer.append("            Optional<"+NAME_CLASS+"> o"+NAME_CLASS+" = "+NAME_CLASS_PROPERTIES+"Service.insert("+NAME_CLASS_PROPERTIES+");\n");
		buffer.append("            if (o"+NAME_CLASS+".isPresent())\n");
		buffer.append("                return ResponseEntity.ok(o"+NAME_CLASS+".get());\n");
		buffer.append("            return new ResponseEntity<>(\"Insert error\", HttpStatus.BAD_REQUEST);\n");
		buffer.append("        } catch (Exception e) {\n");
		buffer.append("            return new ResponseEntity<>(\"Server error\", HttpStatus.BAD_GATEWAY);\n");
		buffer.append("        }\n");
		buffer.append("    }\n");
		buffer.append("\n");
		buffer.append("    @PutMapping(\"/{"+NAME_CLASS_PROPERTIES+"Id}\")\n");
		buffer.append("    public ResponseEntity<?> update(@PathVariable(\""+NAME_CLASS_PROPERTIES+"Id\") "+entity.findKeyIfSingleKey().getTypeProperty()+" "+NAME_CLASS_PROPERTIES+"Id, @RequestBody "+NAME_CLASS+" "+NAME_CLASS_PROPERTIES+") {\n");
		buffer.append("        try {\n");
		buffer.append("            Optional<"+NAME_CLASS+"> o"+NAME_CLASS+" = "+NAME_CLASS_PROPERTIES+"Service.update("+NAME_CLASS_PROPERTIES+"Id, "+NAME_CLASS_PROPERTIES+");\n");
		buffer.append("            if (o"+NAME_CLASS+".isPresent())\n");
		buffer.append("                return ResponseEntity.ok(o"+NAME_CLASS+".get());\n");
		buffer.append("            return new ResponseEntity<>(\"Update error\", HttpStatus.BAD_REQUEST);\n");
		buffer.append("        } catch (Exception e) {\n");
		buffer.append("            return new ResponseEntity<>(\"Server error\", HttpStatus.BAD_GATEWAY);\n");
		buffer.append("        }\n");
		buffer.append("    }\n");
		buffer.append("}\n");
		return buffer.toString();
	}

}
