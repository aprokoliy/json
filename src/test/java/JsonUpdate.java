import com.google.gson.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Map;

public class JsonUpdate {
      //envoke method of updating values of Object in JSON file
    public  void updateJson(String inputfile, String outputfile) throws IOException {

        String content= new String(Files.readAllBytes(Paths.get(inputfile)));
        JsonObject jsonObj = new Gson().fromJson(content,JsonObject.class);
        JsonObject nestedJsonObj = jsonObj.getAsJsonObject("value").getAsJsonObject("form").getAsJsonObject();
        nestedJsonObj.getAsJsonObject("client").addProperty("firstName","Яня");
        nestedJsonObj.getAsJsonObject("client").addProperty("lastName","Холодный");
        nestedJsonObj.getAsJsonObject("client").addProperty("middleName","Иванович");
        nestedJsonObj.getAsJsonObject("client").addProperty("birthDate","07.03.1983");
        nestedJsonObj.getAsJsonObject("contact").addProperty("firstName","Яня");
        nestedJsonObj.getAsJsonObject("contact").addProperty("lastName","Холодный");
        nestedJsonObj.getAsJsonObject("contact").addProperty("middleName","Иванович");
        Files.write(Paths.get(outputfile),jsonObj.toString().getBytes(), StandardOpenOption.CREATE);

    }

    JsonParser parser;
    JsonElement rootNode;
    JsonObject obj;
    Gson gson = new Gson();
    //Igor method of reading  JSON file and updating it, using Iterator, parameter and value
    public void readObj() throws IOException {

        parser = new JsonParser();
        rootNode = parser.parse(new InputStreamReader(new FileInputStream("C:/Users/user/IdeaProjects/json/src/test/resources/request_1.json")));
        obj = rootNode.getAsJsonObject();
        Iterator<Map.Entry<String , JsonElement>> it = obj.entrySet().iterator();
        recursiveSearch(it , "firstName" , "Roma");

        System.out.println(obj.toString());
    }

    public void recursiveSearch(Iterator it, String field, String value) throws IOException {
        while(it.hasNext()){
            Map.Entry<String, JsonElement> element = (Map.Entry<String, JsonElement>) it.next();
            if(element.getKey().equals(field)){
                JsonElement element1 = new JsonPrimitive(value);
                element.setValue(element1);
            }
            else if (element.getValue().isJsonObject()){
                JsonObject root1 = (JsonObject) element.getValue();
                Iterator<Map.Entry<String, JsonElement>> it1 = root1.entrySet().iterator();
                recursiveSearch(it1, field, value);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        JsonUpdate jsup = new JsonUpdate();
      //  jsup.updateJson("C:/Users/user/IdeaProjects/json/src/test/resources/request_1.json", "C:/Users/user/IdeaProjects/json/src/test/resources/request_2.json");
      jsup.readObj();
    }

}
