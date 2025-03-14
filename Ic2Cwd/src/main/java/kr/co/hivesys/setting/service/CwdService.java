package kr.co.hivesys.setting.service;

import java.util.List;

import kr.co.hivesys.setting.vo.CwdVO;

/**
 * 사용자 서비스 클래스
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

public interface CwdService {
	
	//전체 사용자 조회
	public List<CwdVO> selectCwdList(CwdVO CwdVO) throws Exception;
	
	//사용자 등록
	public void insertCwd(CwdVO CwdVO) throws Exception;
	
	//특정 사용자 조회
	public CwdVO selectCwd(CwdVO CwdVO) throws Exception;

	//사용자 정보 수정
	public void updateCwd(CwdVO uvo);

	//사용자 삭제
	public void deleteCwd(List<String> CwdArr);
	
	//상세에서 사용자 삭제
	public void deleteCwdD(String CwdId);
	
}
