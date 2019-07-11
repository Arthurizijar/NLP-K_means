import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> information = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream("./案情输出.txt");
            readTxt(words, fs);
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found!");
        } catch (IOException e) {
            System.out.println("File read error!");
        }
        KMeansTest kmeans = new KMeansTest(words);
        try {
            FileInputStream fs = new FileInputStream("./案情.txt");
            readTxt(information, fs);
        } catch (FileNotFoundException e) {
            System.out.println("File can not be found!");
        } catch (IOException e) {
            System.out.println("File read error!");
        }
        kmeans.setVector(information);
        KMeansData data = new KMeansData(kmeans.vector2Array(), 2566, 99); // 初始化数据结构
        KMeansParam param = new KMeansParam(); // 初始化参数结构
        param.initCenterMehtod = KMeansParam.CENTER_RANDOM; // 设置聚类中心点的初始化模式为随机模式

        // 做kmeans计算，分九类
        KMeans.doKmeans(14, data, param);

        // 查看每个点的所属聚类标号
        System.out.print("The labels of points is: ");
        for (int i = 0; i < data.labels.length; i++) {
            System.out.print(data.labels[i] + "  " + information.get(i));
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
