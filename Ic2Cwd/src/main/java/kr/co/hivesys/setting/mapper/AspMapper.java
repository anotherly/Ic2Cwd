package kr.co.hivesys.setting.mapper;
import java.util.HashMap;
import java.util.List;
import egovframework.rte.psl.dataaccess.mapper.Mapper;
import kr.co.hivesys.setting.vo.AspVO;

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

@Mapper("aspMapper")
public interface AspMapper{
	//전체 회원정보 조회
	public List<AspVO> selectAspList(AspVO AspVO) throws Exception;
	public List<AspVO> selectAspType(AspVO AspVO) throws Exception;
	//특정 회원정보 조회
	public AspVO selectAsp(AspVO AspVO) throws Exception;
	//사용자 등록
	public void insertAsp(AspVO AspVO) throws Exception;
	//사용자 정보 수정
	public void updateAsp(AspVO uvo);
	//사용자 삭제
	public void deleteAsp(HashMap<String, Object> map);
	//상세에서 사용자 삭제
	public void deleteAspD(AspVO AspVO);
	
}
