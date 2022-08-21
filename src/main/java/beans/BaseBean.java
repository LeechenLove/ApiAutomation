package beans;

/**
 * @Author: Lulu
 * @Description: test excel实体类
 * @DateTime: 2022/8/21 9:47
 **/
public class BaseBean {
    private String excelName;

    private String sheetName;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }
}
