import java.util.HashMap;
import java.util.HashSet;

public class Vector {
    private HashMap<String, Integer> vector = new HashMap<>();

    public Vector (HashSet<String> eigenvalue) {
        for (String word : eigenvalue) {
            vector.put(word, 0);
        }
    }

    public void setBooleanTrue (String word) {
        if (vector.containsKey(word)) {
            vector.replace(word, 1);
        }
    }
}
