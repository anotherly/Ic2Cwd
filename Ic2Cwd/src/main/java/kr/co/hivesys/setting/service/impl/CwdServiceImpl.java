package kr.co.hivesys.setting.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.co.hivesys.setting.service.CwdService;
import kr.co.hivesys.setting.mapper.CwdMapper;
import kr.co.hivesys.setting.vo.CwdVO;

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

@Service("cwdService")
public class CwdServiceImpl implements CwdService{
	
	@Resource(name="cwdMapper")
	private CwdMapper CwdMapper;
	
	//전체 회원정보 조회
	public List<CwdVO> selectCwdList(CwdVO CwdVO) throws Exception{
		return CwdMapper.selectCwdList(CwdVO);
	}

	//사용자 등록
	public void insertCwd(CwdVO CwdVO) throws Exception {
		CwdMapper.insertCwd(CwdVO);
	}
	
	//특정 사용자 조회
	public CwdVO selectCwd(CwdVO CwdVO) throws Exception {
		return CwdMapper.selectCwd(CwdVO);
	}

	//사용자 정보 수정
	public void updateCwd(CwdVO uvo) {
		CwdMapper.updateCwd(uvo);
	}

	//사용자 삭제
	public void deleteCwd(List<String> CwdArr) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("chkList",CwdArr);
		CwdMapper.deleteCwd(map);		
	}
	
	// 상세에서 사용자 삭제
	public void deleteCwdD(String CwdId) {
		CwdMapper.deleteCwdD(CwdId);
	}
}
