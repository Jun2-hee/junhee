package com.jn.rest.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//사용시   showView에서  Outline 을 열어서 사용확인하기 
@Setter
@Getter
@ToString
public class BrdVO {
	private int bdId;
	private String bdTitle;
	private String bdCont;
	private String bdWriter;
	private String bdImg;   //파일경로 저장용으로 사용할 것음
	//실제로 파일을 받는것이 필요함
	private MultipartFile bdFile;    //input type="file"의 같은 이름으로 만들어준다.
	
}
