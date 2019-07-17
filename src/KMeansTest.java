import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KMeansTest {
    private HashMap<String, ArrayList<String>> wordMap = new HashMap<>();
    private ArrayList<String> wordSet = new ArrayList<>();
    private HashMap<Integer, HashMap<Integer, Integer>> eigenVector = new HashMap<>();

    private static final String rxWord = "(['])(.*?)(['])";
    //private static final String rxNum = "(?<=['][,]\\s)\\d*(?=[)])";
    private int num = 0;


    public KMeansTest(ArrayList<String> words, ArrayList<String> homoionym) {
        Pattern patWord = Pattern.compile(rxWord);
        //Pattern patNum = Pattern.compile(rxNum);
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i).replace("\n", "");
            String homSentence = homoionym.get(2 * i);
            Matcher matWord = patWord.matcher(homSentence);
            //Matcher matNum = patNum.matcher(word);
            wordSet.add(word);
            wordMap.put(word, new ArrayList<String>());
            ArrayList<String> homSet = wordMap.get(word);
            while (matWord.find()) {
                homSet.add(matWord.group(2));
                homSentence = homSentence.substring(matWord.end());
                matWord = patWord.matcher(homSentence);
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
                } else if (!wordMap.get(word).isEmpty()) {
                    for (int i = 0; i < wordMap.get(word).size(); i++) {
                        String rxHom = ".*" + wordMap.get(word).get(i) + ".*\n";
                        if (sentence.matches(rxHom)) {
                            eigenVector.get(num).put(dim, 10 - i);
                            break;
                        }
                    }
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
