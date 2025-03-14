package kr.co.hivesys.setting.mapper;
import java.util.HashMap;
import java.util.List;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.hivesys.setting.vo.CwdVO;

/**
 * 사용자 매퍼 클래스
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

@Mapper("cwdMapper")
public interface CwdMapper{
	//전체 회원정보 조회
	public List<CwdVO> selectCwdList(CwdVO CwdVO) throws Exception;
	//특정 회원정보 조회
	public CwdVO selectCwd(CwdVO CwdVO) throws Exception;
	//사용자 등록
	public void insertCwd(CwdVO CwdVO) throws Exception;
	//사용자 정보 수정
	public void updateCwd(CwdVO uvo);
	//사용자 삭제
	public void deleteCwd(HashMap<String, Object> map);
	//상세에서 사용자 삭제
	public void deleteCwdD(String CwdId);
	
}
