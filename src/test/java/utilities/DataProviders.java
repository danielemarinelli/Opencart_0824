package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name="LoginData")
    public Object[][] getData() throws IOException {
        String path = ".\\testData\\Opencart_LoginData.xlsx";  // xcel file from testData folder

        ExcelUtils xlutil = new ExcelUtils(path); // creating an object for xlutility
        int totalrows = xlutil.getRowCount("Credentials");
        int totalcols = xlutil.getCellCount("Credentials",1);

        String logindata[][] = new String[totalrows][totalcols]; // created for two dimension array which can store the data from excel file

        for (int i = 1; i <= totalrows; i++) { //read data from the excel file storing in two dimensional array
            for (int j = 0; j < totalcols; j++) {
                logindata[i-1][j] = xlutil.getCellData("Credentials",i,j);
            }
        }

        return logindata;  //returning two dimensional array
    }

}
