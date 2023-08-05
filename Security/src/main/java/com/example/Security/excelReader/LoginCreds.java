package com.example.Security.excelReader;

import com.example.Security.entity.LoginDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LoginCreds {

    public boolean isExcelStudent(MultipartFile file){
        String contentType = file.getContentType();
        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<LoginDetails> converterLogin(InputStream is) throws IOException {
        List<LoginDetails> loginDetailsList = new ArrayList<>();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
            XSSFSheet sheet = xssfWorkbook.getSheet("login");
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
                LoginDetails loginDetails = new LoginDetails();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cid) {
                        case 0:
                            loginDetails.setId((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            loginDetails.setPassword(cell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cid += 1;
                }
                loginDetailsList.add(loginDetails);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return loginDetailsList;
    }


}
