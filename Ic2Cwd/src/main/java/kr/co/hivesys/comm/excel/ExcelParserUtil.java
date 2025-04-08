package kr.co.hivesys.comm.excel;

import kr.co.hivesys.terminal.vo.AfcDataVO;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

// 해당 클래스는 화면에서 업로드한 엑셀 파일을 읽어온다
public class ExcelParserUtil {

    public static List<AfcDataVO> parse(MultipartFile file, int activeCap) throws Exception {
        List<AfcDataVO> dataList = new ArrayList<>();
        String fileName = file.getOriginalFilename();

        // 1. 파일명에서 날짜 추출
        Pattern p = Pattern.compile("\\((\\d{4}\\.\\d{2}\\.\\d{2})\\)");
        Matcher m = p.matcher(fileName);
        String dateStr;
        if (m.find()) {
            dateStr = m.group(1).replace(".", "-"); // yyyy-MM-dd
        } else {
            throw new Exception("파일명에서 날짜 추출 실패");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 2. Excel 읽기
        InputStream is = file.getInputStream();
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);

        // 역명 행 (2번째 행)
        Row rowStation = sheet.getRow(1);

        // 열차별로 3줄 단위로 파싱 (시간/재차인원/혼잡도)
        for (int row = 2; row + 2 <= sheet.getLastRowNum(); row += 3) {
            Row rowTime   = sheet.getRow(row);
            Row rowPeople = sheet.getRow(row + 1);
            Row rowRate   = sheet.getRow(row + 2);

            for (int col = 1; col < rowStation.getLastCellNum(); col++) {
                String stationId = rowStation.getCell(col).getStringCellValue();
                String timeStr = rowTime.getCell(col).getStringCellValue();

                // 정규식으로 HH:mm 또는 H:m 추출 ("0:0" 같은 것도 허용)
                Pattern timePattern = Pattern.compile("\\d{1,2}:\\d{1,2}(:\\d{1,2})?");
                Matcher timeMatcher = timePattern.matcher(timeStr);
                String cleanTime;
                if (timeMatcher.find()) {
                    cleanTime = timeMatcher.group();
                    String[] parts = cleanTime.split(":");
                    int hour = Integer.parseInt(parts[0]);
                    int minute = Integer.parseInt(parts[1]);

                    // 00:00 또는 0:0 같은 값은 무시
                    if (hour == 0 && minute == 0) {
                        continue;
                    }

                    if (parts.length == 2) {
                        cleanTime = String.format("%02d:%02d:00", hour, minute);
                    } else if (parts.length == 3) {
                        int second = Integer.parseInt(parts[2]);
                        cleanTime = String.format("%02d:%02d:%02d", hour, minute, second);
                    } else {
                        continue;
                    }
                } else {
                    continue; // 시간 형식이 없으면 스킵
                }

                String dateTimeStr = dateStr + " " + cleanTime;
                Date rcvDt = sdf.parse(dateTimeStr);

                // 재차인원 파싱
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

                // 혼잡도 파싱
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
                vo.setRcvDt(rcvDt);
                vo.setStationId(stationId);
                vo.setPeopleCnt(peopleCnt);
                vo.setRate(rate);
                vo.setActiveCap(activeCap);

                dataList.add(vo);
            }
        }

        workbook.close();
        is.close();

        return dataList;
    }
}