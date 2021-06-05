import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import opencsv.utils.CsvMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class ConvertCSVtoJson {

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

    public JSONArray convertJavaObjectToJsonArray(ArrayList flights) throws JsonProcessingException {

        JSONArray flightsJson = new JSONArray(flights);
        return flightsJson;
    }
}