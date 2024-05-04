import java.io.*;
import java.util.concurrent.TimeUnit;

public class SoundLevelMonitor {
    public static class RangeChecker {
        private static final int LOW_MIN = Integer.parseInt(System.getenv("LOW"));
        private static final int LOW_MAX = Integer.parseInt(System.getenv("MEDIUM"));
        private static final int MEDIUM_MIN = Integer.parseInt(System.getenv("MEDIUM"));
        private static final int MEDIUM_MAX = Integer.parseInt(System.getenv("MAX"));

        public String checkLevel(double number) {
            if (number >= LOW_MIN && number <= LOW_MAX) {
                return "Low";
            } else if (number > LOW_MAX && number <= MEDIUM_MAX) {
                return "Medium";
            } else if (number > MEDIUM_MAX) {
                return "High";
            } else {
                return "Invalid input";
            }
        }
    }

    public static void main(String[] args) {
        BufferedWriter writer = null;
        BufferedReader reader = null;
        String line = null;
        try {
            writer = new BufferedWriter(new FileWriter("/usr/src/app/data/noisepollution.txt"));

            while (true) {
                reader = new BufferedReader(new FileReader("/usr/src/app/data/soundlevel.txt"));
                int sum = 0;
                int counter = 0;
                while ((line = reader.readLine()) != null) {
                    String[] levels = line.split(" ");
                    for (String level : levels) {
                        sum += Integer.parseInt(level);
                        counter++;
                    }
                }
                double avg = (double) sum / counter;

                RangeChecker rangeChecker = new RangeChecker();
                String level = rangeChecker.checkLevel(avg);

                writer.write(level);
                writer.write(" ");
                writer.flush();
                TimeUnit.SECONDS.sleep(30);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
