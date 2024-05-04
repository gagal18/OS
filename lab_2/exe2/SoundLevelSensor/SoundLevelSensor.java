import java.io.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SoundLevelSensor {
    public static void main(String[] args) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("/usr/src/app/data/soundlevel.txt"));

            Random random = new Random();

            while (true) {
                int randomNumber = random.nextInt(100 - 40 + 1) + 40;
                writer.write(Integer.toString(randomNumber));
                writer.write(" ");
                writer.flush();
                TimeUnit.SECONDS.sleep(20);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
