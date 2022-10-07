package Section20;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataDriven {

    public ArrayList<String> getData(String sheetName, String testCaseName) {

        ArrayList<String> list = new ArrayList<>();

        FileInputStream file = null;
        XSSFWorkbook workbook;
        try {
            file = new FileInputStream("src/main/java/Section20/Demo Data.xlsx");
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int sheetCount = workbook.getNumberOfSheets();

        for (int i=0; i<sheetCount; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);

                Iterator<Row> rowIterator = sheet.iterator();
                Row firstRow = rowIterator.next();

                Iterator<Cell> cellIterator = firstRow.cellIterator();

                int column = 0;
                int k = 0;

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getStringCellValue().equalsIgnoreCase("TestCases")) {
                        column = k;
                    }
                    k++;
                }
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if(row.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                        Iterator<Cell> cellValue = row.cellIterator();
                        while (cellValue.hasNext()) {
                            Cell c = cellValue.next();
                            if (c.getCellType() == CellType.STRING) {
                                list.add(c.getStringCellValue());
                            } else {
                                list.add(NumberToTextConverter.toText(c.getNumericCellValue()));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        DataDriven dd = new DataDriven();
        ArrayList data = dd.getData("Rest Assured", "RestAddBook");

        System.out.println(data.get(0));
        System.out.println(data.get(1));
        System.out.println(data.get(2));
        System.out.println(data.get(3));
        System.out.println(data.get(4));
    }
}
