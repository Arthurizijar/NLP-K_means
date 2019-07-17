import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> information = new ArrayList<>();
        ArrayList<String> homoionym = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream("./案情特征词v1.1.txt");
            readTxt(words, fs);
        } catch (FileNotFoundException e) {
            System.out.println("File 案情特征词 can not be found!");
        } catch (IOException e) {
            System.out.println("File read error!");
        }
        try {
            FileInputStream fs = new FileInputStream("./案情特征词近义词v1.0.txt");
            readTxt(homoionym, fs);
        } catch (FileNotFoundException e) {
            System.out.println("File 案情近义词 can not be found!");
        } catch (IOException e) {
            System.out.println("File read error!");
        }
        KMeansTest kmeans = new KMeansTest(words, homoionym);
        try {
            FileInputStream fs = new FileInputStream("./案情.txt");
            readTxt(information, fs);
        } catch (FileNotFoundException e) {
            System.out.println("File 案情 can not be found!");
        } catch (IOException e) {
            System.out.println("File read error!");
        }
        kmeans.setVector(information);
        KMeansData data = new KMeansData(kmeans.vector2Array(), information.size(), words.size()); // 初始化数据结构
        KMeansParam param = new KMeansParam(); // 初始化参数结构
        param.initCenterMehtod = KMeansParam.CENTER_RANDOM; // 设置聚类中心点的初始化模式为随机模式

        // 做kmeans计算，分十四类
        KMeans.doKmeans(14, data, param);

        // 创建Excel对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("聚类情况");
        // 查看每个点的所属聚类标号
        //System.out.print("The labels of points is: ");
        HSSFRow title = sheet.createRow(0);
        title.createCell(0).setCellValue("编号");
        title.createCell(1).setCellValue("聚类类号");
        title.createCell(2).setCellValue("人工分类");
        title.createCell(3).setCellValue("案情");
        for (int i = 0; i < data.labels.length; i++) {
            HSSFRow row = sheet.createRow(i + 1);
            String[] cutter = information.get(i).split(",");
            row.createCell(0).setCellValue(cutter[0]);
            row.createCell(1).setCellValue(data.labels[i]);
            row.createCell(2).setCellValue(cutter[1]);
            row.createCell(3).setCellValue(cutter[2]);
            //System.out.print(data.labels[i] + "  " + information.get(i));
        }
        try {
            FileOutputStream output = new FileOutputStream("./案情输出.xls");
            workbook.write(output);
            output.flush();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private static void readTxt(ArrayList<String> words, FileInputStream fs) throws IOException {
        String str;
        InputStreamReader isr = new InputStreamReader(fs, "GB2312");
        BufferedReader br = new BufferedReader(isr);
        while ((str = br.readLine()) != null) {
            str = str + "\n";
            words.add(str);
        }
    }

}
