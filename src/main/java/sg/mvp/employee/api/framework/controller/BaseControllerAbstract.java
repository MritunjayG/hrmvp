package sg.mvp.employee.api.framework.controller;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import sg.mvp.employee.api.framework.ApiResponse;

public class BaseControllerAbstract {
	
	public ResponseEntity<ApiResponse> returnResponseEntity(ApiResponse result){
		
		return ResponseEntity.ok()
				.contentType(new MediaType("application", "json",
						Charset.forName("UTF-8"))).body(result);
				
	}

}
