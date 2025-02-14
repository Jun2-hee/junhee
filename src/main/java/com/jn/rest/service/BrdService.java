package com.jn.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jn.rest.mapper.BrdMapper;
import com.jn.rest.vo.BrdVO;

// 인터페이스가 아닌 실제 구현체에 @Service를 추가해준다.


// 서비스는 보통 비지니스 로직구현 레이어
// I/F , xxxImpl구분해서 만들었는데, 쓸데 없이 I/F가 많이 만들어지는 경우가 많아서
// 바로 구현체 class로 만드는 경우가 점점 많아짐!
// 서비스에선 Mapper(DAO)를 부른다.
@Service
public class BrdService {
	@Autowired      //spring이 new 해주기 위해서 무조건 기입해줘야한다.
	private BrdMapper brdMapper;
	
	//조회 list
	public List<BrdVO> getList(){
		return brdMapper.getList();
	} 
	
	//조회 vo 
	public BrdVO getBrd(int bdId) {
		return brdMapper.getBrd(bdId);
	} 
	 
	//등록
	public int insBrd(BrdVO brdVO){
		return brdMapper.insBrd(brdVO);
	}	
	
	//수정
	public int upBrd(BrdVO brdVO){
		return brdMapper.upBrd(brdVO);
	}	
	
	//삭제
	public int delBrd(int bdId){
		return brdMapper.delBrd(bdId);
	}	
	
	
	
	
	
	
}
