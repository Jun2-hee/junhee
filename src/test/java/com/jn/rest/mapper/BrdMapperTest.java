package com.jn.rest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jn.rest.vo.BrdVO;

//table-> Vo-> mapper i/f -> sql 작성-> 테스트
@SpringBootTest          //자바네서 유명한 테스트 프레임워트 jUnit5 부터 테스트프레임워크라고 불리운다.
public class BrdMapperTest {
	//@Autowired new에서 생성해야하는것은 알아서 생성해준다.
	@Autowired
	private BrdMapper brdMapper;
	
	//조회(여러개 가져오기)
	@Test
	@DisplayName("Brd List조회 테스트")
	@Disabled
	void getListTest() {
		assertEquals(14, brdMapper.getList().size());
	}
	
	//조회 (한개 가져오기)
	@Test
	@DisplayName("Brd One조회 테스트")
	@Disabled
	void getBrdTest() {
		// 메소드 return 타입에 주목
		// 매게변수와 return 타입이 머릿속에 잘 정리 안된사람은 후다닥 다시 정리해
		assertEquals("수정 제목", brdMapper.getBrd(8).getBdTitle());
	}
	
	
	//한개 등록
	@Test    //jUnit:테스트프레임워크 (서버를 실행하지않아도 확인가능하다) 자바를 실행하지않고도 확인이 가능하다는 장점 
	@DisplayName("Brd 추가 테스트")     //그냥한다면 insBrdTest로 들어가지만 ""안의 이름으로 변경할수있다.
	@Disabled          //테스트가 끝나서 실행하지않을경우사용하면 이 어놑테이션을 사용하면 실행되지 않음
	void insBrdTest() {
		BrdVO brdVO = new BrdVO();
		brdVO.setBdTitle("나는제목이다.");
		brdVO.setBdCont("내용");
		brdVO.setBdWriter("작성자");
		
		int cnt =  brdMapper.insBrd(brdVO);
		
		// test시 자주사용함   assert: 단정하다 ~여야만 하다
		// 진짜 테스트    기댓값과, 실제값이 같아야한다.   같이않다면 테스트에 불합격이다.
		//pass와 fail이 판단된다.
		//assertEquals("기대값", "실제값");
		assertEquals(1, cnt);
	}
	
	
	
	//여러개 등록
	@Test    //jUnit:테스트프레임워크 (서버를 실행하지않아도 확인가능하다) 자바를 실행하지않고도 확인이 가능하다는 장점 
	@DisplayName("Brd 여러개 입력후")     //그냥한다면 insBrdTest로 들어가지만 ""안의 이름으로 변경할수있다.
	@Disabled          //테스트가 끝나서 실행하지않을경우사용하면 이 어놑테이션을 사용하면 실행되지 않음
	void insBrdsTest() {
		BrdVO brdVO = null;
		int sum = 15;
		int realSum=0;
		for(int i=1; i<=15; i++) {
	
			brdVO = new BrdVO();
			brdVO.setBdCont("내용 : "+i);
			brdVO.setBdTitle("제목 : "+i);
			brdVO.setBdWriter("작성자 : "+i);
			realSum+= brdMapper.insBrd(brdVO);
		}
		//진자 테스트 판단 Pass와 Fail
		assertEquals(sum, realSum);
	}
	
	//수정
	@Test
	@DisplayName("Brd 수정 테스트")
	@Disabled  //더이상 실행되지 않게 
	void upBrdsTest() {
		BrdVO brdVO = new BrdVO();
		brdVO.setBdId(8);
		brdVO.setBdTitle("수정 제목");
		brdVO.setBdCont("수정 내용");
		brdVO.setBdWriter("수정 작성자");
	
		
		assertEquals(1, brdMapper.upBrd(brdVO));
	}
	
	
	//삭제
	@DisplayName("Brd 삭제 테스트")
	@Test
	@Disabled   
	void delBrdTest() {
		assertEquals(1, brdMapper.delBrd(22));
	}
	
	
}
