package com.example.entex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Random;
import java.nio.charset.Charset;

import com.example.entex.EntexApplication;
import java.util.Optional;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.io.*;
import java.lang.*;

import java.util.UUID;

@Controller 
@RequestMapping(path="/demo")
public class MainController {
  
  String token = "";

  @Autowired
  private CountyRepository countyRepository;
  
  @Autowired
  private LocalityRepository localityRepository;

  @Autowired
  private UserRepository userRepository;

  public void addCounties(String countyCsvFile){
  		boolean skipFirst = true;
		List<List<String>> records = new ArrayList<>();
                try{
			BufferedReader br = new BufferedReader(new FileReader(countyCsvFile));
			String line;
			while ((line = br.readLine()) != null) {
                            if(skipFirst){
                               skipFirst = false;
                               continue;
                            }

			    String[] values = line.split(",");
                            County c = new County();

                            c.setCountyId(Integer.parseInt(values[0].substring(1,values[0].length()-1)));
                            c.setCountyName(values[1].substring(1,values[1].length()-1));
                            c.setCountyCode(values[2].substring(1,values[2].length()-1));
                            countyRepository.save(c);
			}
                } catch (FileNotFoundException e){
                        System.out.println(e.toString());
                } catch (IOException e){
                	System.out.println(e.toString());
                }              
  }

  public void addLocalities(String localityCsvFile){
  		boolean skipFirst = true;
		List<List<String>> records = new ArrayList<>();
                try{
			BufferedReader br = new BufferedReader(new FileReader(localityCsvFile));
			String line;
			while ((line = br.readLine()) != null) {
                            if(skipFirst){
                               skipFirst = false;
                               continue;
                            }

			    String[] values = line.split(",");
                            Locality c = new Locality();

                            c.setLocalityId(Integer.parseInt(values[0].substring(1,values[0].length()-1)));
                            c.setLocalityName(values[1].substring(1,values[1].length()-1));
                            c.setCountyCode(values[2].substring(1,values[2].length()-1));
                            localityRepository.save(c);
			}
                } catch (FileNotFoundException e){
                        System.out.println(e.toString());
                } catch (IOException e){
                	System.out.println(e.toString());
                }              
  }


  @GetMapping(path="/populate_database") // Map ONLY POST Requests
  public @ResponseBody String addNewCounty () {
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST request
    	if(EntexApplication.localitiesAdded == false){
    		this.addLocalities(EntexApplication.localityCsvFile);
		EntexApplication.localitiesAdded = true;	
	}

        if(EntexApplication.countiesAdded == false){
    		this.addCounties(EntexApplication.countyCsvFile);
		EntexApplication.countiesAdded = true;
	}
    return "Success";
  }


  @PostMapping(path="/user")
  public @ResponseBody ResponseEntity addNewUser(@RequestHeader("token") String token, @RequestParam String name, @RequestParam String email, @RequestParam String locality_name, @RequestParam String password){
	if(!this.token.equals(token)){
 	       return new ResponseEntity("Token invalid.", HttpStatus.FORBIDDEN);
    	}
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setLocalityName(locality_name);
        user.setPassword(password);
        
        try{
        	List<Locality> localitiesList = localityRepository.findLocalityBylocalityName(locality_name);
		String countyCode = localitiesList.get(0).getCountyCode();
		List<County> countyList = countyRepository.findCountyBycountyCode(countyCode);

        	user.setCountyName(countyList.get(0).getCountyName());
	} catch(Exception e){
		return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

        try{
        	userRepository.save(user);
        } catch(DataIntegrityViolationException e){
                System.out.println(e.toString());
                return new ResponseEntity("User with email " + email + " already exists", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(user, HttpStatus.OK);
  }  

  @PutMapping(path="/user")
  public @ResponseBody ResponseEntity updateNewUser(@RequestHeader("token") String token, @RequestParam(required=true) Integer id, @RequestParam(required=false) String name,
		 @RequestParam(required=false) String email, @RequestParam(required=false) String locality_name, 
		@RequestParam(required=false) String password){

	if(!this.token.equals(token)){
       		return new ResponseEntity("Token invalid.", HttpStatus.FORBIDDEN);
    	}
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty()){
		return new ResponseEntity("User with id does not exist", HttpStatus.NOT_FOUND);
        }

	User user = userOptional.get();
        if(name != null){
        	user.setName(name);
        }

	if(email != null){
        	user.setEmail(email);
	}

	if(locality_name != null){
        	user.setLocalityName(locality_name);
	}

	if(password != null){
        	user.setPassword(password);
        }

	if(locality_name != null){
		try{
			List<Locality> localitiesList = localityRepository.findLocalityBylocalityName(locality_name);
			String countyCode = localitiesList.get(0).getCountyCode();
			List<County> countyList = countyRepository.findCountyBycountyCode(countyCode);

			user.setCountyName(countyList.get(0).getCountyName());
		} catch(Exception e){
			return new ResponseEntity(e.toString(), HttpStatus.NOT_FOUND);
		}
	}

        try{
        	userRepository.save(user);
        } catch(DataIntegrityViolationException e){
                System.out.println(e.toString());
                return new ResponseEntity("User with email " + email + " already exists", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(user, HttpStatus.OK);
  }  

  @GetMapping(path="/user/details")
  public String getAllUserDetailPage(Model model) {
    Iterable<User> userList = userRepository.findAll();
    model.addAttribute("userList", userList);
    return "details2";
  }

  @PostMapping(path="/login")
  public @ResponseBody ResponseEntity login(@RequestParam String username, @RequestParam String password) {
    if(username.equals("administrator") && password.equals("password")){
        UUID uuid = UUID.randomUUID();
	this.token = "" + uuid;
        return new ResponseEntity(this.token, HttpStatus.OK);
    } else {
	return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
  }

  @GetMapping(path="/user")
  public @ResponseBody ResponseEntity getAllUsers(@RequestHeader("token") String token) {
    if(!this.token.equals(token)){
       return new ResponseEntity("Token invalid.", HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity(userRepository.findAll(), HttpStatus.OK);
  }

  @GetMapping(path="/user", params = "name")
  public @ResponseBody ResponseEntity getUser(@RequestHeader("token") String token, @RequestParam String name) {
    if(!this.token.equals(token)){
       return new ResponseEntity("Token invalid.", HttpStatus.FORBIDDEN);
    }
    return new ResponseEntity(userRepository.findUserByname(name), HttpStatus.OK);
  }

  @DeleteMapping(path="/user")
  public @ResponseBody ResponseEntity deleteUser(@RequestHeader("token") String token, @RequestParam Integer id) {
    if(!this.token.equals(token)){
       return new ResponseEntity("Token invalid.", HttpStatus.FORBIDDEN);
    }
    try{
    	userRepository.deleteById(id);
    } catch (Exception e){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity("Deletion was succesfull.", HttpStatus.OK);
  }

}
