import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class ConvertCSVtoJson {

    // считываем .csv файл в ArrayList
    public ArrayList readCsvFile(String fileWay) throws IOException {

        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        ArrayList flights = new ArrayList();
        fileReader = new BufferedReader(new FileReader(fileWay));
        csvParser = new CSVParser(fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
        ArrayList<CSVRecord> csvRecords = (ArrayList<CSVRecord>) csvParser.getRecords();

        for (int i = 0; i < csvRecords.size(); i++) {

            CSVRecord csvRecord = csvRecords.get(i);
            Flights flight = new Flights(
                    csvRecord.get("id"),
                    csvRecord.get("origin"),
                    csvRecord.get("destination"),
                    csvRecord.get("departureDate"),
                    csvRecord.get("departureTime"),
                    csvRecord.get("arrivalDate"),
                    csvRecord.get("arrivalTime"),
                    csvRecord.get("number"));

            flights.add(flight);
        }

        fileReader.close();
        csvParser.close();
        return flights;
    }

    // конвертируем ArrayList в JSONArray
    public JSONArray convertJavaObjectToJsonArray(ArrayList flights) throws JsonProcessingException {

        return new JSONArray(flights);
    }

    // конвертируем строку в формат времени HH:mm
    public String convertStringToDateFormat (Object jsonObj) {

        String res = null;
        int hours = 0;
        int minutes = 0;

        if( jsonObj.toString().length() == 4) {
             hours = Integer.parseInt(jsonObj.toString())/100;
             minutes = Integer.parseInt(jsonObj.toString())%100;
             res = hours + ":" + minutes;
        }
        else {
             hours = Integer.parseInt(jsonObj.toString())/100;
             minutes = Integer.parseInt(jsonObj.toString())%100;
             res = "0" + hours + ":" + minutes;
        }

        return res;
        }

        // проверяем, есть ли элемент в базе
        public String checkElementInBase (String nameOfElement, String element, JSONArray jsonArray) {

            String isElementHere = null;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.get(nameOfElement).equals(element)) isElementHere = element;
            }
            return isElementHere;
        }
    }
