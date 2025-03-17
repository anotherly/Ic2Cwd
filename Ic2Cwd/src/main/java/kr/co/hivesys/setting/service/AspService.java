package kr.co.hivesys.setting.service;

import java.util.List;

import kr.co.hivesys.setting.vo.AspVO;

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

public interface AspService {
	
	//전체 사용자 조회
	public List<AspVO> selectAspList(AspVO AspVO) throws Exception;
	public List<AspVO> selectAspType(AspVO AspVO) throws Exception;
	
	//사용자 등록
	public void insertAsp(AspVO AspVO) throws Exception;
	
	//특정 사용자 조회
	public AspVO selectAsp(AspVO AspVO) throws Exception;

	//사용자 정보 수정
	public void updateAsp(AspVO uvo);

	//사용자 삭제
	public void deleteAsp(List<String> AspArr);
	
	//상세에서 사용자 삭제
	public void deleteAspD(AspVO AspVO);
	
}
