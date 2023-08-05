package com.studentVersion2.Studentversion2.helper;

import com.studentVersion2.Studentversion2.dto.SemesterMarksResponse;
import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SemesterExcel {

    public boolean isExcelStudent(MultipartFile file){
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public SemesterMarksResponse converterStudent(InputStream is) throws IOException {
        SemesterMarksResponse semesterMarksResponse = new SemesterMarksResponse();
        List<String> missingCells = new ArrayList<>();
        try{
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheet("sem");
            int rowNumber=0;
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (rowNumber == 0) {
                    rowNumber += 1;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cid = 0;
                SemesterMarks semesterMarks = new SemesterMarks();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellType = "";
                    if(cell.getCellType() == CellType.STRING){
                        cellType = cell.getStringCellValue();
                    }
                    else if(cell.getCellType() == CellType.NUMERIC){
                        cellType = String.valueOf(cell.getNumericCellValue());
                    }
                    if(cellType.isEmpty()){
                        missingCells.add(getCellIndex(cell));
                        cid += 1;
                        continue;
                    }
                    switch (cid) {
                        case 0:
                            semesterMarks.setId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            semesterMarks.getSemIds().add((int) cell.getNumericCellValue());
                            break;
                        case 2:
                            semesterMarks.getSize().add((int) cell.getNumericCellValue());
                            break;
                        case 3:
                            semesterMarks.getSubjects().add(cell.getStringCellValue());
                            break;
                        case 4:
                            semesterMarks.getSubMarks().add((float) cell.getNumericCellValue());
                            break;
                        case 5:
                            semesterMarks.getSubjects().add(cell.getStringCellValue());
                            break;
                        case 6:
                            semesterMarks.getSubMarks().add((float) cell.getNumericCellValue());
                            break;
                        case 7:
                            semesterMarks.getSemIds().add((int) cell.getNumericCellValue());
                            break;
                        case 8:
                            semesterMarks.getSize().add((int) cell.getNumericCellValue());
                            break;
                        case 9:
                            semesterMarks.getSubjects().add(cell.getStringCellValue());
                            break;
                        case 10:
                            semesterMarks.getSubMarks().add((float) cell.getNumericCellValue());
                            break;
                        case 11:
                            semesterMarks.getSubjects().add(cell.getStringCellValue());
                            break;
                        case 12:
                            semesterMarks.getSubMarks().add((float) cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid += 1;
                }
                semesterMarksResponse.getSemesterMarksList().add(semesterMarks);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        semesterMarksResponse.setMissingCells(missingCells);
        return semesterMarksResponse;
    }
    private String getCellIndex(Cell cell){
        int rowIndex = cell.getRowIndex()+1;
        int columnIndex = cell.getColumnIndex()+1;
        return "R" + rowIndex + "C" + columnIndex;
    }
}


