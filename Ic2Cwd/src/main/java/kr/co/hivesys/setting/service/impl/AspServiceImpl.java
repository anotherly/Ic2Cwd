package kr.co.hivesys.setting.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.hivesys.setting.service.AspService;
import kr.co.hivesys.setting.mapper.AspMapper;
import kr.co.hivesys.setting.vo.AspVO;

/**
 * 사용자 서비스 구현 클래스
 * @author 솔루션사업팀 정다빈
 * @since 2021.07.23
 * @version 1.0
 * @see
 *
 * << 개정이력(Modification Information) >>
 *
 *   수정일            수정자              수정내용
 *  -------    -------- ---------------------------
 *  2021.07.23  정다빈           최초 생성
 */

@Service("aspService")
public class AspServiceImpl implements AspService{
	
	@Resource(name="aspMapper")
	private AspMapper aspMapper;
	
	//전체 회원정보 조회
	public List<AspVO> selectAspList(AspVO AspVO) throws Exception{
		return aspMapper.selectAspList(AspVO);
	}
	public List<AspVO> selectAspType(AspVO AspVO) throws Exception{
		return aspMapper.selectAspType(AspVO);
	}

	//사용자 등록
	public void insertAsp(AspVO AspVO) throws Exception {
		aspMapper.insertAsp(AspVO);
	}
	
	//특정 사용자 조회
	public AspVO selectAsp(AspVO AspVO) throws Exception {
		return aspMapper.selectAsp(AspVO);
	}

	//사용자 정보 수정
	public void updateAsp(AspVO uvo) {
		aspMapper.updateAsp(uvo);
	}

	//사용자 삭제
	public void deleteAsp(List<String> AspArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",AspArr);
		aspMapper.deleteAsp(map);		
	}
	
	// 상세에서 사용자 삭제
	public void deleteAspD(AspVO AspVO) {
		aspMapper.deleteAspD(AspVO);
	}
}
