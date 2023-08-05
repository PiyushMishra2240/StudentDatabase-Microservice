package com.studentVersion2.Studentversion2.helper;

import com.studentVersion2.Studentversion2.entity.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentExcel {
    public boolean isExcelStudent(MultipartFile file){
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Student> converterStudent(InputStream is){
        List<Student> studentList = new ArrayList<>();
        try{
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheet("Data");
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
                Student student = new Student();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cid) {
                        case 0:
                            student.setId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            student.setCourse(cell.getStringCellValue());
                            break;
                        case 2:
                            student.setName(cell.getStringCellValue());
                            break;
                        case 3:
                            student.setTotalSemesters((int) cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid += 1;
                }
                studentList.add(student);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return studentList;
    }
}
