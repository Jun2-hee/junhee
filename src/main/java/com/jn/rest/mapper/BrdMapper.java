package com.jn.rest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jn.rest.vo.BrdVO;

@Mapper        //단순마커(표시) mapper라고 알려주는역활을 하는것 표시해주는것 특별한 기능은 없다.
public interface BrdMapper {
	// get2개 조회(1개),(여러개) 등록(post) 수정(update) 삭제(delete)   
	// 똑똑한 사람은 시키지 않아도 기본적으로 최소 5개의 메소드 선언을 만든다.  
	// 조회(2개)  
	//select 의 경우 여기 , I/F에서 return 타입으로 selectList, selectOne 이나가 결정함
	List<BrdVO> getList();     //list 
	BrdVO getBrd(int bdId);	 	//vo     
	//멋진말로 mutation 메소드라 불리는 것들 쓰기, insert/update/delete
	int insBrd(BrdVO brdVO);
	int upBrd(BrdVO brdVO);
	int delBrd(int bdId);
}
