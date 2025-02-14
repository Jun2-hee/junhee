package com.jn.rest.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
//@RestController에는 @Target,@Controller, @ResponseBody,@Retention,@Documented가 포함되어있다..
//@RestController는 접수창구이기 때문에 따로 이름 부르는 것이 필요하다.
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jn.rest.mapper.BrdMapper;
import com.jn.rest.service.BrdService;
import com.jn.rest.vo.BrdVO;
import com.jn.rest.vo.MaguVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j    //모두 나에게 풀어주겠음~~~~~!!!
@CrossOrigin("http://localhost:5173")     //ajax의 제약사항인 CrossOrigin제약사항을 모두에게 풀어주겠다라는것을 의미한다.
@RestController  			//@ResponseBody가 포함되어있다. @Target(value={TYPE})@Controller@ResponseBody@Retention(value=RUNTIME)@Documented
@RequestMapping("/api")
public class BrdController {
	
	@Autowired
	private BrdService brdService ;
	//생성자 주입을 강요하는것 
	
	//조회 (여러개)
	@GetMapping("/bds")
	public List<BrdVO> getList(){
		log.debug("getList에서 왔어?");
		return brdService.getList();
		
	}
	
	//조회 1개 id 기준
	@GetMapping("/bds/{bdId}")
	public BrdVO getBrd(
			//이렇게 사용하기만 서로 같다면 그냥 한개만 입력해도됨
			@PathVariable("bdId") int jhId) throws Exception {
		log.debug("bdId: {}",jhId);
		/*
		 * Jackson라이브러리(전자정부표준라이브러리) 동작 확인, @RequestBody와 @ResponseBody에 자동으로 동작
		 */
		
		ObjectMapper objMapper = new ObjectMapper();      //잭슨라이브러리가 있는 변환기 
		
		BrdVO jnVo = new BrdVO();
		jnVo.setBdId(337);
		jnVo.setBdTitle("준희제목");
		jnVo.setBdCont("준희내용");
		jnVo.setBdWriter("준희");
		
		//이것은 @ResponseBody의 동작 java-> json로 변경   
		String jsonString = objMapper.writeValueAsString(jnVo);     //객체를 json문자열로....
		
		
		//@RequestBody의 동작     json-> java로 변경(자바객체 보통 VO)
		String sampleString = "{\"bdId\":111,\"bdTitle\":\"제목이지롱\",\"bdCont\":\"내용이지롱\",\"bdWriter\":\"희준\"}";
		BrdVO testVO =objMapper.readValue(sampleString, BrdVO.class);
		log.debug("눈으로 확인1 {}",testVO.getBdWriter());
		log.debug("눈으로 확인2 {}",testVO);
		log.debug("눈으로 json확인{}" ,jsonString);
		return brdService.getBrd(jhId);
	}
	
	//등록 파일이 없는경우만 사용가능
	@PostMapping("/bds")
	// 브라우저에서 넘어온 JSON문자열 <=> 자바객체 jackson라이브러리, GSON등등 
	// @RequestBody를 사용하여 json문자열로 변화  되게 만들어야 한다.   jackson라이브러리가 이런것을 대신해준다.
	public String insBrd(@RequestBody BrdVO brdVO) {
		log.debug("brdVO :{}",brdVO);
		int cnt =brdService.insBrd(brdVO);
		String result = "success";
		if(cnt ==0) {
			result="fail";
		}
		return result;
		
	}
	
	//브라우저에서 FormData(binary  formdata/multi-part)가 넘어왔을때 사용하는  등록
	//@RequestPart는 formData를 보낼경우 part의 일부분을 받을수 있다.
	//RequestPart-> MultipartParser가 먼저 넘어온걸 해석한 뒤에 , 해당 키값에 대한 문자열만 
	// jackson라이브러리에 넘기면서 자바객체로 변환됨!
	@PostMapping("/bds2")
	public String insBrd2(BrdVO brdVO 
						  ,@RequestPart("young")List<String> junList
						  ,@RequestPart("sein") Map<String, Object> seinMap
						  ,@RequestPart("mix") List<MaguVO> maguList
						  
						  ) throws Exception {
		log.debug("part - junList 체킁 : "+junList);
		log.debug("part - seinMap 체킁 : "+seinMap);
		log.debug("part - maguList 체킁 : "+maguList);
		
		log.debug("brdVO : {}",brdVO);
		log.debug("bdFile : {}",brdVO.getBdFile());
		log.debug("bdFile 이름 : {}",brdVO.getBdFile().getOriginalFilename());
		
		//넘어온값중에 파일 만 뺀다
		MultipartFile jhFile = brdVO.getBdFile();
		
		//서버 하드디스크에 저장 , 웹경로와 물리적경로 맵핑이 필요하다. 왜? 물리적 경로 안알려줄것이니까~
		String saveDir = "d:/jnUp/";      //실제 저장경로
		jhFile.transferTo(new File(saveDir+jhFile.getOriginalFilename()));
		
		
		String webUrl = "/jnweb/"+jhFile.getOriginalFilename();
		//웹경로를 BdImg 이 속성에 저장한다.
		brdVO.setBdImg(webUrl);
		
		//등록 되는곳
		brdService.insBrd(brdVO);
		
		//이것은 DB에 들어가야 한다.
		return webUrl;
		
		
		
	}
	
	//수정
	@PutMapping("/bds")
	
	public String upBrd(@RequestBody BrdVO brdVO) {
		log.debug("brdVO :{}",brdVO);
		int cnt= brdService.upBrd(brdVO);
		String result = "success";
		if(cnt ==0) {
			result="fail";
		}
		return result;
		
	}
	
	
	//수정     ajax를 위한 수정2
	@PutMapping("/bds2")
	
	public String upBrd2(BrdVO brdVO) throws Exception {
		log.debug("brdVO :{}",brdVO);
		
		//파일수정을 위한 로직 시작
		//넘어온값중에 파일 만 뺀다
		MultipartFile jhFile = brdVO.getBdFile();
		
		//서버 하드디스크에 저장 , 웹경로와 물리적경로 맵핑이 필요하다. 왜? 물리적 경로 안알려줄것이니까~
		String saveDir = "d:/jnUp/";      //실제 저장경로
		jhFile.transferTo(new File(saveDir+jhFile.getOriginalFilename()));
		
		
		String webUrl = "/jnweb/"+jhFile.getOriginalFilename();
		//웹경로를 BdImg 이 속성에 저장한다.
		brdVO.setBdImg(webUrl);
		
		//파일수정을 위한 로직끝

		
		int cnt= brdService.upBrd(brdVO);
		String result = "success";
		if(cnt ==0) {
			result="fail";
		}
		return result;
		
	}
	
	
	
	
	
	
	//삭제
	@DeleteMapping("/bds/{bdId}")
	public String delBrd(@PathVariable int bdId) {
		log.debug("bdId :{}",bdId);
		int cnt= brdService.delBrd(bdId);
		String result = "success";
		if(cnt == 0) {
			result="fail";
		}
		return result;
	}
	
}
