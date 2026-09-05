package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class JsonReader {

    public static Map<String, Map<String, String>> getLoginData(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = JsonReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IOException(fileName + " not found in classpath");
            }
            return mapper.readValue(input, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
