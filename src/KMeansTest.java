import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KMeansTest {
    private HashMap<String, Integer> wordMap = new HashMap<>();
    private ArrayList<String> wordSet = new ArrayList<>();
    private HashMap<Integer, HashMap<Integer, Integer>> eigenVector = new HashMap<>();

    private static final String rxWord = "(?<=[(][']).*(?=['])";
    private static final String rxNum = "(?<=['][,]\\s)\\d*(?=[)])";
    private int num = 0;



    public KMeansTest(ArrayList<String> words) {
        Pattern patWord = Pattern.compile(rxWord);
        Pattern patNum = Pattern.compile(rxNum);
        for (String word : words) {
            Matcher matWord = patWord.matcher(word);
            Matcher matNum = patNum.matcher(word);
            if (matWord.find() && matNum.find()) {
                wordMap.put(matWord.group(), Integer.parseInt(matNum.group()));
                wordSet.add(matWord.group());
            }
        }
    }

    public void setVector(ArrayList<String> information) {
        for (String sentence : information) {
            eigenVector.put(num, new HashMap<>());
            int dim = 0;
            for (String word : wordSet) {
                String rx = ".*" + word + ".*\n";
                if (sentence.matches(rx)) {
                    eigenVector.get(num).put(dim, 10);
                } else {
                    eigenVector.get(num).put(dim, 0);
                }
                dim++;
            }
            num++;
        }
    }

    public double[][] vector2Array() {
        double[][] calculate = new double[eigenVector.size()][wordSet.size()];
        for (Integer id : eigenVector.keySet()) {
            HashMap<Integer, Integer> temp = eigenVector.get(id);
            for (Integer dim : temp.keySet()) {
                calculate[id][dim] = temp.get(dim);
            }
        }
        return calculate;
    }


}
