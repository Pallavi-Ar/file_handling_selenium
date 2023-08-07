package implementation;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonArray;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.sl.Ce;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

import static implementation.Main.properties;

public class Parse {
    public static void main(String[] args) throws IOException {
        //calling propertie function
        properties();
        //giving file path
        String filePath = Main.filePath;
        //fetching workbook and sheet
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet = workbook.getSheetAt(0);

        //creating a new jsonArray
        JsonArray jsonArray = new JsonArray();
        //traversing the sheet
        for(Row row : sheet) {
            //creating a jsonObject
            JsonObject jsonObject = new JsonObject();
            //traversing rows
            for(Cell cell : row) {
                //Getting the value of column
                String columnName = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                //getting the value of cell
                String cellValue = cell.getStringCellValue();

                //storing column and cell in jsonObject
                jsonObject.add(columnName,cellValue);
            }
            //adding jsonObject to jsonArray
            jsonArray.add(jsonObject);
        }
        //converting jsonArray to String
        String jsonData = jsonArray.toString();
        //printing data
        System.out.println(jsonData);
    }
}
