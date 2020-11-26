 代码如下：

```java
//代码如下：
import net.sf.json.JSONArray;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel表格转成json
 */
public class Excel2JSONHelper {

    //常亮，用作第一种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_Z = 1;
    //第二种模板类型，如下图
    private static final int HEADER_VALUE_TYPE_S = 2;

    public static void main(String[] args) {
        File dir = new File("D:\\成绩导入模板.xls");
        Excel2JSONHelper excelHelper = getExcel2JSONHelper();
        //dir文件，0代表是第一行为保存到数据库或者实体类的表头，一般为英文的字符串，2代表是第二种模板，  
        JSONArray jsonArray = excelHelper.readExcle(dir, 0, 2);
        System.out.println(jsonArray.toString());
    }


    /**
     * 获取一个实例
     * @return .
     */
    public static Excel2JSONHelper getExcel2JSONHelper() {
        return new Excel2JSONHelper();
    }


    /**
     * 文件过滤
     * @param file .
     * @return .
     */
    private boolean fileNameFileter(File file) {
        boolean endsWith = false;
        if (file != null) {
            String fileName = file.getName();
            endsWith = fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
        }
        return endsWith;
    }


    /**
     * 获取表头行
     * @param sheet .
     * @param index .
     * @return  .
     */
    private Row getHeaderRow(Sheet sheet, int index) {
        Row headerRow = null;
        if (sheet != null) {
            headerRow = sheet.getRow(index);
        }
        return headerRow;
    }


    /**
     * 获取表格中单元格的value
     * @param row   .
     * @param cellIndex .
     * @param formula   .
     * @return  .
     */
    private Object getCellValue(Row row, int cellIndex, FormulaEvaluator formula) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null) {
            switch (cell.getCellType()) {
                //String类型
                case Cell.CELL_TYPE_STRING:
                    return cell.getRichStringCellValue().getString();
                //number类型
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().getTime();
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
		       return df.format(cell.getNumericCellValue());
                    }
                    //boolean类型
                case Cell.CELL_TYPE_BOOLEAN:
                    return cell.getBooleanCellValue();
                //公式    
                case Cell.CELL_TYPE_FORMULA:
                    return formula.evaluate(cell).getNumberValue();
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * 获取表头value
     * @param headerRow .
     * @param cellIndex 英文表头所在的行，从0开始计算哦
     * @param type      表头的类型第一种 姓名（name）英文于实体类或者数据库中的变量一致
     * @return .
     */
    private String getHeaderCellValue(Row headerRow, int cellIndex, int type) {
        Cell cell = headerRow.getCell(cellIndex);
        String headerValue = null;
        if (cell != null) {
            //第一种模板类型
            if (type == HEADER_VALUE_TYPE_Z) {
                headerValue = cell.getRichStringCellValue().getString();
                int l_bracket = headerValue.indexOf("（");
                int r_bracket = headerValue.indexOf("）");
                if (l_bracket == -1) {
                    l_bracket = headerValue.indexOf("(");
                }
                if (r_bracket == -1) {
                    r_bracket = headerValue.indexOf(")");
                }
                headerValue = headerValue.substring(l_bracket + 1, r_bracket);
            } else if (type == HEADER_VALUE_TYPE_S) {
                //第二种模板类型
                headerValue = cell.getRichStringCellValue().getString();
            }
        }
        return headerValue;
    }


    /**
     * 读取Excle表
      * @param file Excle文件
     * @param headerIndex .
     * @param headType  表头的类型第一种 姓名（name）英文于实体类或者数据库中的变量一致
     * @return .
     */
    public JSONArray readExcle(File file, int headerIndex, int headType) {
        List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
        if (!fileNameFileter(file)) {
            return null;
        } else {
            try {
                //加载excel表格
                WorkbookFactory wbFactory = new WorkbookFactory();
                Workbook wb = wbFactory.create(file);
                //读取第一个sheet页
                Sheet sheet = wb.getSheetAt(0);
                //读取表头行
                Row headerRow = getHeaderRow(sheet, headerIndex);
                //读取数据
                FormulaEvaluator formula = wb.getCreationHelper().createFormulaEvaluator();
                for (int r = headerIndex + 1; r <= sheet.getLastRowNum(); r++) {
                    Row dataRow = sheet.getRow(r);
                    Map<String, Object> map = new HashMap<String, Object>();
                    for (int h = 0; h < dataRow.getLastCellNum(); h++) {
                        //表头为key
                        String key = getHeaderCellValue(headerRow, h, headType);
                        //数据为value
                        Object value = getCellValue(dataRow, h, formula);
                        if (!key.equals("") && !key.equals("null") && key != null) {
                            map.put(key, value);
                        }
                    }
                    lists.add(map);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(lists);
        return jsonArray;
    }
}
```

 

 

 

excel表格模板类型和调用方式 ** 

第一种 ：用括号把实体类变量名称或者数据库字段名称括起来 

![img](assets/wps1.jpg) 

调用方法如下： 

//表格的名称为2003.xls 

File file= new File("e:\\2003.xls");

 Excel2JSONHelper excelHelper = getExcel2JSONHelper();

 //字母表头为在第1行，第1种模板类型

 JSONArray jsonArray = excelHelper.readExcle(file, 1, 1);

 

 

 第二种： 实体类变量名称或者数据库字段另起一行，如下两张图都行

![img](assets/wps2.jpg) 

调用方法如下： 

//表格的名称为2003.xls 

File file= new File("e:\\2003.xls");

 Excel2JSONHelper excelHelper = getExcel2JSONHelper(); 

//字母表头为在第1行，第2种模板类型 

JSONArray jsonArray = excelHelper.readExcle(file, 1, 2); 

 

 

 

 

![img](assets/wps3.jpg) 

//表格的名称为2003.xls 

File file= new File("e:\\2003.xls"); 

Excel2JSONHelper excelHelper = getExcel2JSONHelper(); 

//字母表头为在第2行，第2种模板类型 

JSONArray jsonArray = excelHelper.readExcle(file, 2, 2); 

 

 

jsonArray打印的结果 [{"index":"1","name":"李逵","jobNum":"10004","dept":"开发部","job":"android工程师"}, {"index":"2","name"....

 

 

 