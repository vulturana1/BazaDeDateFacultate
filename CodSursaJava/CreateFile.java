import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateFile {
        public void main(String[] args) {
            try {
                LocalDateTime time = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy");
                String s2=time.format(formatter).toString();
                s2+=".txt";
                File myObj = new File(s2);
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
