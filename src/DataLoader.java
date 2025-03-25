import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private final String fileName;

    public DataLoader(String fileName) {
        this.fileName = fileName;
    }

    public void loadData(ArrayList<List<Double>> inputs, ArrayList<Integer> outputs, Map<String, Integer> labels) {
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] tokens = line.split(",");
                int n = tokens.length;
                List<Double> attributes = new ArrayList<>();
                for (int j = 0; j < n - 1; j++) {
                    attributes.add(Double.parseDouble(tokens[j].trim()));
                }

                String labelName = tokens[n - 1];
                int label;
                if (labels.isEmpty()) {
                    label = 1;
                } else {
                    label = labels.getOrDefault(labelName, 0);
                }
                labels.put(labelName, label);

                inputs.add(attributes);
                outputs.add(label);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getDimensions() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            int dimensions = in.readLine()
                    .split(",")
                    .length - 1;
            in.close();
            return dimensions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
