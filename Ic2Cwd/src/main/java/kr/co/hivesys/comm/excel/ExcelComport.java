package kr.co.hivesys.comm.excel;

import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.*;
import kr.co.hivesys.terminal.vo.TerminalVo;

public class ExcelComport {


    // 테이블 속성제목 부 디자인 (제목 스타일)
    public XSSFCellStyle titleStyle(XSSFWorkbook workbook) {
        XSSFCellStyle titleStyle = workbook.createCellStyle();

        // 정렬
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 배경색
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 테두리 선 (우,좌,위,아래)
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);

        return titleStyle;
    }

    // 테이블 내용 부 디자인 (내용 셀 스타일)
    public XSSFCellStyle contentStyle(XSSFWorkbook workbook) {
        XSSFCellStyle contentStyle = workbook.createCellStyle();

        // 테두리 선
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        // 정렬
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return contentStyle;
    }
	
	//
	/**
	 * 기본 테이블 엑셀 다운로드 기능
	 * 주의 ! 해당 메소드는 페이지에 표출되는 테이블 형식대로 
	 * 고정 크기와 고정 컬럼값을 지니므로
	 * 셀 병합,특정 셀의 크기 변동등의 별도의 핸들링이 필요하다면
	 * 따로 짜야한다... 
	 * */
	public XSSFWorkbook createDfExcelContent
	(HashMap<Integer, String> thMap, HashMap<Integer, Map> tbMap) {
	    
	    int x=1;//열 인덱스 -> 0부터 시작 1칸 띄우고 시작
		int y=1;//행 인덱스 -> 0부터 시작 1칸 띄우고 시작
		
		//엑셀 객체 생성
		XSSFWorkbook workbook = new XSSFWorkbook();
		//엑셀 시트 생성
		XSSFSheet sheet1 = workbook.createSheet("sheet1");//들어가는 파라미터는 시트명임
		//시트너비 (고정)
		for (int i = 0; i < thMap.size(); i++) {
			sheet1.setColumnWidth(i+1, 3000);//약 2500~3000이 기본 엑셀 가로넓이라고 생각하면 됨
		}
	    //엑셀 행렬 객체 생성
	    XSSFRow objRow = sheet1.createRow(y); //행 (y)
	    XSSFCell objCel=null;
	    //th 디자인 
	    XSSFCellStyle titleStyle = titleStyle(workbook); 
	    //tb 디자인 
	    XSSFCellStyle contentStyle = contentStyle(workbook); 
	    
	    //행에 대한 열 생성 / 해당 열에 값 삽입
	    //th 부분
	    for (int i = 0; i < thMap.size(); i++) {
	    	objCel=objRow.createCell(x+i);
	    	objCel.setCellStyle(titleStyle);
	    	objCel.setCellValue(thMap.get(i));
		}
	    y++;
	    //tbody 부분
	    for (int i = 0; i < tbMap.size(); i++) {
	    	objRow = sheet1.createRow(y+i);
	    	HashMap<Integer, String> tbSubMap = new HashMap<Integer, String>();
	    	tbSubMap=(HashMap<Integer, String>) tbMap.get(i);
	    	for (int j = 0; j < tbSubMap.size(); j++) {
	    		objCel=objRow.createCell(x+j);
		    	objCel.setCellStyle(contentStyle);
		    	objCel.setCellValue(tbSubMap.get(j));
			}
	    }
		return workbook;
	}
	
	//별도 afc 데이터 엑셀 다운을 위한 별도제작 함수
    public XSSFWorkbook exportExcel(List<TerminalVo> tList,List<TerminalVo> sList) throws Exception {
    	//엑셀 객체 생성
    	XSSFWorkbook workbook = new XSSFWorkbook();

    	Sheet sheet = workbook.createSheet("열차혼잡도");

        // 역명과 편성번호 목록 생성
        List<String> stationNames = new ArrayList<>();

        // 데이터 매핑 (stationName -> formationNo -> TerminalVo)
        Map<String, Map<String, TerminalVo>> dataMap = new LinkedHashMap<>();

//일단 하행부터 생각해보자...
        
        // 첫 번째 행 (헤더): 역명
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("구분");
        int colIdx = 1;
        for (int i = 0; i < sList.size(); i++) {
        	if(sList.get(i).getActiveCap().equals("2")) {
        		headerRow.createCell(colIdx++).setCellValue(sList.get(i).getStationName());
        	}
        }

        int rowIdx = 1;
        // 두 번째 행부터 편성번호별로 재차인원과 혼잡도를 번갈아 입력
        colIdx=1;
        Row rdtKpaRow = sheet.createRow(rowIdx);
        Row sumKpaRow = sheet.createRow(rowIdx);
        Row rateAvgRow = sheet.createRow(rowIdx);
        Row rowt1 = sheet.createRow(rowIdx);
        Row rowt2 = sheet.createRow(rowIdx);
        
        //최초에 기점과 편성이 매핑된 경우
        String rightFno="";
        //역사 기점-종점 완료횟수
        int endCnt=0;
        //전체 데이터 반복
    	for (int i = 0; i < tList.size(); i++) {
    		TerminalVo vo = tList.get(i);
    		if(i==0) {
    			rightFno=vo.getFormationNo();
    		}
    		//상단 역사 반복
			for (int j = 0; j < sList.size(); j++) {
				String station = sList.get(j).getStationName();
				//역사정보가 데이터와 같고 데이터의 편성번호가 현재 채워지는 편성번호일때
				if(vo.getStationName().equals(station) && vo.getFormationNo().equals(rightFno)) {
					if(j==0) {//기점
						 colIdx=sList.size()*2;
						 
						 rdtKpaRow = sheet.createRow(rowIdx++);
			             sumKpaRow = sheet.createRow(rowIdx++);
			             rateAvgRow = sheet.createRow(rowIdx++);
						 
						 rdtKpaRow.createCell(0).setCellValue(vo.getFormationNo() + "편성 : ");
						 rdtKpaRow.createCell(0).setCellValue(vo.getFormationNo() + "역명 : ");
						 rdtKpaRow.createCell(0).setCellValue(vo.getFormationNo() + "편성 : 시각");
				         sumKpaRow.createCell(0).setCellValue(vo.getFormationNo() + "편성 : 무게합계");
				         rateAvgRow.createCell(0).setCellValue(vo.getFormationNo()  + "편성 : 혼잡도(%)");
					}
					if(colIdx>0) {
						int sumKpa = vo.getSumKpa();
						rowt1.createCell(colIdx).setCellValue(vo.getFormationNo());
						rowt2.createCell(colIdx).setCellValue(vo.getStationName());
			            rdtKpaRow.createCell(colIdx).setCellValue(vo.getRcvDt());
			            sumKpaRow.createCell(colIdx).setCellValue(sumKpa);
			            rateAvgRow.createCell(colIdx).setCellValue(vo.getAvgCwd());
			            colIdx--;
					}
					
		            //종점까지 다 채워진 시첨
		            if(vo.getStationName().equals("검단오류")){
		            	//완료횟수 +1
		            	endCnt++;
		            	i=i-endCnt;
		            }
				}
			}
			//상 하행에 따라 기->종으로 이동 시 초기화 구문 필요함
		}
        
    	
    	
    	
        /*for (String formationNo : formationNos) {
            Row rdtKpaRow = sheet.createRow(rowIdx++);
            Row sumKpaRow = sheet.createRow(rowIdx++);
            Row rateAvgRow = sheet.createRow(rowIdx++);

            rdtKpaRow.createCell(0).setCellValue("시각");
            sumKpaRow.createCell(0).setCellValue(formationNo + " 재차인원");
            rateAvgRow.createCell(0).setCellValue(formationNo + " 혼잡도(%)");

            colIdx = 1;
            for (String station : stationNames) {
                TerminalVo vo = dataMap.get(station).get(formationNo);
                if (vo != null) {
                    int sumKpa = vo.getKpa1() + vo.getKpa2() + vo.getKpa3() + vo.getKpa4();
                    rdtKpaRow.createCell(colIdx).setCellValue(vo.getRcvDt());
                    sumKpaRow.createCell(colIdx).setCellValue(sumKpa);
                    rateAvgRow.createCell(colIdx).setCellValue((vo.getRate1()+vo.getRate2())/2);
                } else {
                	rdtKpaRow.createCell(colIdx).setCellValue("");
                    sumKpaRow.createCell(colIdx).setCellValue("");
                    rateAvgRow.createCell(colIdx).setCellValue("");
                }
                colIdx++;
            }
        }*/
    	
    	
        return workbook;
    }
	
    
    public XSSFWorkbook exportExcel7(List<TerminalVo> tList, List<TerminalVo> sList) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("열차혼잡도");

        // 1. 정거장 순서 유지
        List<String> stationNames = new ArrayList<>();
        for (TerminalVo vo : sList) {
            if (!stationNames.contains(vo.getStationName())) {
                stationNames.add(vo.getStationName());
            }
        }

        // 2. 헤더
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("구분");
        for (int i = 0; i < stationNames.size(); i++) {
            headerRow.createCell(i + 1).setCellValue(stationNames.get(i));
        }

        // 3. formation별 그룹화
        Map<String, List<TerminalVo>> byFormation = new HashMap<>();
        for (TerminalVo vo : tList) {
            byFormation.computeIfAbsent(vo.getFormationNo(), k -> new ArrayList<>()).add(vo);
        }

        List<RunBlock> allRuns = new ArrayList<>();

        for (String formationNo : byFormation.keySet()) {
            List<TerminalVo> sortedList = byFormation.get(formationNo);
            sortedList.sort(Comparator.comparing(TerminalVo::getRcvDt));

            List<TerminalVo> currentRun = new ArrayList<>();
            String direction = null;

            for (int i = 0; i < sortedList.size(); i++) {
                TerminalVo vo = sortedList.get(i);
                String station = vo.getStationName();
                direction = vo.getActiveCap();

                boolean isStartStation = ("2".equals(direction) && "검단오류".equals(station)) ||
                                         ("1".equals(direction) && "운연".equals(station));

                if (isStartStation && !currentRun.isEmpty()) {
                    String runTime = currentRun.get(0).getRcvDt();
                    allRuns.add(new RunBlock(formationNo, runTime, new ArrayList<>(currentRun), direction));
                    currentRun.clear();  // 기점부터는 다음 run
                    continue; // 기점은 포함하지 않음
                }

                currentRun.add(vo);
            }

            // 마지막 남은 run도 저장
            if (!currentRun.isEmpty()) {
                String runTime = currentRun.get(0).getRcvDt();
                allRuns.add(new RunBlock(formationNo, runTime, new ArrayList<>(currentRun), direction));
            }
        }

        // 4. 정렬
        allRuns.sort(Comparator.comparing(r -> r.rcvDt));

        // 5. 출력
        int rowIdx = 1;
        for (RunBlock runBlock : allRuns) {
            String formationNo = runBlock.formationNo;
            String direction = runBlock.direction;
            Map<String, TerminalVo> runMap = new HashMap<>();
            for (TerminalVo vo : runBlock.voList) {
                runMap.put(vo.getStationName(), vo);
            }

            Row timeRow = sheet.createRow(rowIdx++);
            Row sumRow = sheet.createRow(rowIdx++);
            Row rateRow = sheet.createRow(rowIdx++);

            timeRow.createCell(0).setCellValue(formationNo + " 시각 " + runBlock.rcvDt);
            sumRow.createCell(0).setCellValue(formationNo + " 재차인원");
            rateRow.createCell(0).setCellValue(formationNo + " 혼잡도(%)");

            List<String> orderedStations = new ArrayList<>(stationNames);
            if ("1".equals(direction)) {
                Collections.reverse(orderedStations); // 상행은 역순
            }

            for (int i = 0; i < orderedStations.size(); i++) {
                String station = orderedStations.get(i);
                TerminalVo vo = runMap.get(station);
                int colIdx = i + 1;

                Cell c1 = timeRow.createCell(colIdx);
                Cell c2 = sumRow.createCell(colIdx);
                Cell c3 = rateRow.createCell(colIdx);

                if (vo != null) {
                    int sumKpa = vo.getKpa1() + vo.getKpa2() + vo.getKpa3() + vo.getKpa4()
                               + vo.getKpa5() + vo.getKpa6() + vo.getKpa7() + vo.getKpa8();
                    c1.setCellValue(vo.getRcvDt());
                    c2.setCellValue(sumKpa);
                    c3.setCellValue(vo.getAvgCwd());
                } else {
                    c1.setCellValue("");
                    c2.setCellValue("");
                    c3.setCellValue("");
                }
            }
        }

        return workbook;
    }

    private static class RunBlock {
        String formationNo;
        String rcvDt;
        List<TerminalVo> voList;
        String direction;

        RunBlock(String formationNo, String rcvDt, List<TerminalVo> voList, String direction) {
            this.formationNo = formationNo;
            this.rcvDt = rcvDt;
            this.voList = voList;
            this.direction = direction;
        }
    }

    
	//다운로드를 위한 헤더 핸들링
	public void excelDownload(HttpServletRequest req, HttpServletResponse res,String fileName,XSSFWorkbook workbook) {
		try {
			// 브라우저에 따른 파일명 인코딩
	        String userAgent = req.getHeader("User-Agent");
	        if (userAgent.indexOf("MSIE") > -1) {
	            fileName = URLEncoder.encode(fileName, "UTF-8");
	        } else {
	            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        }
	        res.setContentType("application/vnd.ms-excel; charset=euc-kr");
	        res.setHeader("Content-Description" , "JSP Generated Data");
	        res.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
	        
	        	
	        ServletOutputStream out= res.getOutputStream();
	        workbook.write(out);
	        out.flush();
	        out.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
