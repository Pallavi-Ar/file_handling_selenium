package implementation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.time.Duration;
import java.util.Properties;

public class Main {
    //creating required variables
    static Properties properties = new Properties();
    static WebDriver driver;
    public static String chromePath;
    public static String amazonUrl;
    public static String filePath;

    //properties for config.properties
    public static void properties() {
        try (InputStream inputStream = new FileInputStream("src/test/resources/config.properties")) {
            properties.load(inputStream);
            chromePath = properties.getProperty("chromeDriverPath");
            amazonUrl = properties.getProperty("url");
            filePath = properties.getProperty("file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //function to set chromedriver and open url
    public static void initialize() {
        properties();
        System.setProperty("webdriver.chrome.driver", chromePath);
        driver = new ChromeDriver();
        //maximizing the window
        driver.manage().window().maximize();

        //opening the url
        driver.get(amazonUrl);

        //waiting for 10 seconds for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    //function to get and store data in excel
    public static void writeData() {
        try {
            //locating Input box
            WebElement input = driver.findElement(By.xpath("//input[contains(@id, 'twotab')]"));
            //sending Puma
            input.sendKeys("Puma");
            //searching
            WebElement search = driver.findElement(By.xpath("//input[contains(@id, 'nav-search')]"));
            search.click();
            //finding products of puma
            WebElement puma1 = driver.findElement(By.xpath("//span[contains(text(), 'Slipper')]"));
            String pumaIdp = puma1.getText();
            WebElement puma2 = driver.findElement(By.xpath("//span[contains(text(), 'Casual')]"));
            String pumaSoft = puma2.getText();
            WebElement puma3 = driver.findElement(By.xpath("//span[contains(text(), 'Atlas')]"));
            String pumaDraco = puma3.getText();

            driver.navigate().back();
            //searching Adidas
            Thread.sleep(1000);

            //searching Adidas
            WebElement input1 = driver.findElement(By.xpath("//input[contains(@id, 'twotab')]"));
            input1.sendKeys("Adidas");
            WebElement search1 = driver.findElement(By.xpath("//input[contains(@id, 'nav-search')]"));
            search1.click();

           //getting products of adidas
           WebElement adidas1 = driver.findElement(By.xpath("//span[contains(text(), 'Steady')]"));
           String adidasSupa = adidas1.getText();
           WebElement adidas2 = driver.findElement(By.xpath("//span[contains(text(), 'Classigy')]"));
           String adidasClassigy = adidas2.getText();
           WebElement adidas3 = driver.findElement(By.xpath("//span[contains(text(), 'Courun')]"));
           String adidasCou = adidas3.getText();

           //quitting the instance
           driver.quit();

           //making matrix of data to send to excel
           String[][] data = {
                   {"Puma","Adidas"},
                   {pumaIdp, adidasSupa},
                   {pumaSoft, adidasClassigy},
                   {pumaDraco, adidasCou},
           };

           //giving path of empty file
           File file = new File(filePath);
           //creating new workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            //creating sheet inside workbook
            XSSFSheet sheet = workbook.createSheet("Data");
            int rowNum = 0;
            //traversing data
            for(String[] rowData : data) {
                //creating rows
                XSSFRow row = sheet.createRow(rowNum++);
                int colNum = 0;
                //traversing rows
                for(String cellData : rowData) {
                    //creating columns
                    Cell cell = row.createCell(colNum++);
                    //sending data to columns
                    cell.setCellValue(cellData);
                }
            }
            //writing data into excel using FileOutputStream
            try(FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //calling the functions in Main
    public static void main(String[] args) {
        initialize();
        writeData();
    }
}
