package AWSAPIChaning;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {    
	String BaseURI = "http://18.208.164.169:8088/employees";
	@Test
	public void test1() {
	Response response; 
	 
		response = GetMethodAll(); 
	Assert.assertEquals(response.getStatusCode(), 200); 
		 
		response = PostMethod("Andrew", "Gupta", "2000", "andrew@xyz.com"); 
		Assert.assertEquals(response.getStatusCode(), 201);  		
		JsonPath Jpath =response.jsonPath(); 
    int EmpID = Jpath.get("id"); 
     System.out.println("id"+EmpID); 
      
     response = PutMethod(EmpID, "Andrew", "Gupta", "2000", "andrew@xyz.com"); 
    Assert.assertEquals(response.getStatusCode(), 200); 
    Jpath =response.jsonPath(); 
     String firstName = Jpath.get("firstName"); 
     Assert.assertEquals(firstName, "Andrew"); 
    System.out.println("Updated firstName is "+ firstName); 
    String lastName = Jpath.get("lastName"); 
     Assert.assertEquals(lastName, "Gupta"); 
     System.out.println("Updated lastName is "+ lastName); 
     
     response = DeleteMethod(EmpID); 
     String ResponseBody = response.getBody().asString(); 
		Assert.assertEquals(ResponseBody, "");		int ResponseCode = response.getStatusCode(); 
		Assert.assertEquals(ResponseCode, 200); 
		 
response = GetMethod(EmpID); 
	    Assert.assertEquals(response.getStatusCode(), 400); 
		Jpath =response.jsonPath(); 
		Assert.assertEquals(Jpath.get("message"), "Entity Not Found"); 
		 
	} 
 
	public Response GetMethodAll() { 
		 
		RestAssured.baseURI = BaseURI; 
		RequestSpecification request = RestAssured.given();  		
		Response response = request.get(); 
		 
	return response; 
		 
	} 
	 
	public Response PostMethod(String firstName, String lastName, String salary, String email) { 
		RestAssured.baseURI = BaseURI; 
		 	  		JSONObject jobj = new JSONObject();  		  		
		 	  		jobj.put("firstName", firstName); 
		            jobj.put("lastName", lastName); 
		            jobj.put("salary", salary); 
		            jobj.put("email", email); 
		 
	RequestSpecification request = RestAssured.given(); 
		 
	Response response = request.contentType(ContentType.JSON) 
					.accept(ContentType.JSON) 
					.body(jobj.toString()) 
				.post(); 
		 
	return response; 
	} 
public Response PutMethod(int EmpID, String firstName, String lastName, String salary, String email) { 
		RestAssured.baseURI = BaseURI; 
		 
	 
		JSONObject jobj = new JSONObject(); 
		 
		jobj.put("firstName", firstName); 
		jobj.put("lastName", lastName); 
	jobj.put("salary", salary); 
		jobj.put("email", email); 
		 
		RequestSpecification request = RestAssured.given(); 
		 
	Response response = request.contentType(ContentType.JSON) 
					.accept(ContentType.JSON) 
					.body(jobj.toString()) 
				.put("/" + EmpID); 
		 
		return response; 
	} 
	 
	public Response DeleteMethod(int EmpID) { 
		 
		RestAssured.baseURI = BaseURI; 
		RequestSpecification request = RestAssured.given(); 
		Response response = request.delete("/" + EmpID); 
		 
		return response; 
	} 
	 
public Response GetMethod(int EmpID) { 
		 
		RestAssured.baseURI = BaseURI; 
		RequestSpecification request = RestAssured.given(); 
		Response response = request.get("/" + EmpID); 
		 
		return response; 
	} 
	 
} 
























	
		
		