package kr.co.hivesys.comm.excel;

import kr.co.hivesys.afc.vo.AfcDataVO;
import kr.co.hivesys.afc.vo.VsTypeVO;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelParserUtil {
	//afc 데이터 엑셀 파일 업로드 시 엑셀내용 파싱
    public static List<AfcDataVO> parse(MultipartFile file, int activeCap) throws Exception {
        List<AfcDataVO> dataList = new ArrayList<>();
        String fileName = file.getOriginalFilename();

        // 1. 파일명에서 날짜 추출
        Pattern p = Pattern.compile("\\((\\d{4}\\.\\d{2}\\.\\d{2})\\)");
        Matcher m = p.matcher(fileName);
        String dateStr;
        if (m.find()) {
            dateStr = m.group(1).replace(".", "-");
        } else {
            throw new Exception("파일명에서 날짜 추출 실패");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate baseDate = LocalDate.parse(dateStr, dateFormatter);

        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        Row rowStationName = sheet.getRow(1); // 역명
        Row rowStationId = sheet.getRow(0);   // 역번호

        for (int row = 2; row + 2 <= sheet.getLastRowNum(); row += 3) {
            Row rowTime = sheet.getRow(row);       // 시간
            Row rowPeople = sheet.getRow(row + 1); // 재차인원
            Row rowRate = sheet.getRow(row + 2);   // 혼잡도

            // 열차번호는 이 rowTime의 0번째 셀에서 추출
            String trainNo = "";
            Cell trainCell = rowTime.getCell(0);
            if (trainCell != null && trainCell.getCellType() == CellType.STRING) {
                String raw = trainCell.getStringCellValue();
                Matcher trainMatcher = Pattern.compile("(\\d{4})호").matcher(raw);
                if (trainMatcher.find()) {
                    trainNo = trainMatcher.group(1);
                }
            }

            for (int col = 1; col < rowStationName.getLastCellNum(); col++) {
                String timeStr = "";

                Cell cellTime = rowTime.getCell(col);
                if (cellTime != null && cellTime.getCellType() == CellType.STRING) {
                    String raw = cellTime.getStringCellValue();
                    Matcher timeMatcher = Pattern.compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?").matcher(raw);
                    if (timeMatcher.find()) timeStr = timeMatcher.group();
                }

                if (trainNo.isEmpty() || timeStr.isEmpty()) continue;

                String[] parts = timeStr.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);
                int second = (parts.length == 3) ? Integer.parseInt(parts[2]) : 0;

                boolean addOneDay = hour >= 24;
                if (addOneDay) hour -= 24;

                if (hour == 0 && minute == 0) continue;

                String cleanTime = String.format("%02d:%02d:%02d", hour, minute, second);
                LocalDate finalDate = addOneDay ? baseDate.plusDays(1) : baseDate;
                String dateTimeStr = finalDate.format(dateFormatter) + " " + cleanTime;
                Date uptDt = sdf.parse(dateTimeStr);

                // 역명 + 역번호 파싱
                String stationName = "";
                int originalStationId = 0;
                int computedStationId = 0;
                Cell cellStationName = rowStationName.getCell(col);
                Cell cellStationId = rowStationId.getCell(col);

                if (cellStationName != null && cellStationName.getCellType() == CellType.STRING) {
                    stationName = cellStationName.getStringCellValue().trim();
                }
                if (cellStationId != null && cellStationId.getCellType() == CellType.NUMERIC) {
                    originalStationId = (int) cellStationId.getNumericCellValue();
                } else if (cellStationId != null && cellStationId.getCellType() == CellType.STRING) {
                    String val = cellStationId.getStringCellValue().trim();
                    if (!val.isEmpty()) originalStationId = Integer.parseInt(val);
                }

                // 계산된 stationId
                if (originalStationId > 3200) {
                    if (activeCap == 1) {
                        computedStationId = 2 * (originalStationId - 3200) + 1;
                    } else {
                        computedStationId = 2 * (originalStationId - 3200) + 2;
                    }
                }

                // 인원
                int peopleCnt = 0;
                Cell cellPeople = rowPeople.getCell(col);
                if (cellPeople != null) {
                    if (cellPeople.getCellType() == CellType.NUMERIC) {
                        peopleCnt = (int) cellPeople.getNumericCellValue();
                    } else if (cellPeople.getCellType() == CellType.STRING) {
                        String val = cellPeople.getStringCellValue().trim();
                        if (!val.isEmpty()) peopleCnt = Integer.parseInt(val);
                    }
                }

                // 혼잡도
                double rate = 0.0;
                Cell cellRate = rowRate.getCell(col);
                if (cellRate != null) {
                    if (cellRate.getCellType() == CellType.NUMERIC) {
                        rate = cellRate.getNumericCellValue();
                    } else if (cellRate.getCellType() == CellType.STRING) {
                        String val = cellRate.getStringCellValue().trim();
                        if (!val.isEmpty()) rate = Double.parseDouble(val);
                    }
                }

                AfcDataVO vo = new AfcDataVO();
                vo.setUptDt(uptDt);
                vo.setStationId(String.valueOf(computedStationId));
                vo.setStationName(stationName);
                vo.setPeopleCnt(peopleCnt);
                vo.setRate(rate);
                vo.setActiveCap(Integer.toString(activeCap));
                vo.setTrainNo(trainNo);

                dataList.add(vo);
            }
        }

        workbook.close();
        is.close();

        return dataList;
    }
    //편성별 kpa afc 엑셀 파일 업로드 시 엑셀내용 파싱
    public static  List<VsTypeVO> parseVsTypeExcel(MultipartFile file) throws Exception {
        List<VsTypeVO> list = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = WorkbookFactory.create(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                VsTypeVO vo = new VsTypeVO();
                vo.setId((int) row.getCell(0).getNumericCellValue());
                vo.setKpa((int) row.getCell(1).getNumericCellValue());
                vo.setAfc((int) row.getCell(2).getNumericCellValue());
                vo.setAfc2((int) row.getCell(3).getNumericCellValue());
                list.add(vo);
            }
        }

        return list;
    }
    
    public static void downloadVsExcel(HttpServletRequest req,HttpServletResponse res, List<VsTypeVO> dataList) throws Exception {
    	XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("vs_type");

        // 헤더 생성
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("id");
        header.createCell(1).setCellValue("kpa");
        header.createCell(2).setCellValue("afc");
        header.createCell(3).setCellValue("afc2");

        // 데이터 삽입
        int rowNum = 1;
        for (VsTypeVO vo : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vo.getId());
            row.createCell(1).setCellValue(vo.getKpa());
            row.createCell(2).setCellValue(vo.getAfc());
            row.createCell(3).setCellValue(vo.getAfc2());
        }
        ExcelComport ex =new ExcelComport();
        String fileName = "편성별 응하중 및 재차인원";
		ex.excelDownload(req,res,fileName,workbook);
    }
}
