package com.studentVersion2.Studentversion2.helper;

import com.studentVersion2.Studentversion2.entity.Department;
import com.studentVersion2.Studentversion2.entity.SemesterMarks;
import com.studentVersion2.Studentversion2.repository.SemesterMarksRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class DepartmentExcel {
    public boolean isExcelDept(MultipartFile file){
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<Department> converterDept(InputStream is){
        List<Department> departmentList = new ArrayList<>();
        try{
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheet("departmentData");
            int rownumber=0;
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()){
                Row row = rowIterator.next();
                if(rownumber == 0){
                    rownumber += 1;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cid=0;
                Department department = new Department();
                int sem = 0;
                Map<Integer, Integer> semWiseMFullMarks = new HashMap<>();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cid){
                        case 0:
                            department.setId((int)cell.getNumericCellValue());
                            break;
                        case 1:
                            department.setDepartmentName(cell.getStringCellValue());
                            break;
                        default:
                            if(sem == 0){
                                semWiseMFullMarks.put((int) cell.getNumericCellValue(), 0);
                                sem = (int) cell.getNumericCellValue();
                            }
                            else{
                                semWiseMFullMarks.replace(sem, (int) cell.getNumericCellValue());
                                sem = 0;
                            }
                            break;
                    }
                    cid += 1;
                }
                department.setSemWiseFullMarks(semWiseMFullMarks);
                departmentList.add(department);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return departmentList;
    }
}
