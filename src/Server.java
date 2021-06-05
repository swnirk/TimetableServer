import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Server started!");

            while (true) {
                // ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем все что было отправлено клиентом
                    System.out.println();
                    String request = input.readLine();
                    System.out.println(request);
                    String id = request.split("/")[2].split(" ")[0];

                    ConvertCSVtoJson convert = new ConvertCSVtoJson();
                    ArrayList fltArray = new ArrayList();
                    fltArray = convert.readCsvFile("C:\\Users\\79651\\Desktop\\timetable.csv");
                    JSONArray fltArrayJson = new JSONArray();
                    fltArrayJson = convert.convertJavaObjectToJsonArray(fltArray);
                    for (int i = 0; i < fltArrayJson.length(); i++) {
                        JSONObject fltObj = fltArrayJson.getJSONObject(i);
                        if (fltObj.get("id").equals(id)) {
                            output.println("HTTP/1.1 200 OK");
                            output.println("Content-Type: text/html; charset=utf-8");
                            output.println();
                            output.println("<p>Your flight is </p>" + fltObj.get("number"));
                            output.println("<p>Departure time is </p>" + fltObj.get("departureTime"));
                            output.println("<p>Arrival time is </p>" + fltObj.get("arrivalTime"));
                        }
                    }

                    output.flush();

                    // по окончанию выполнения блока try-with-resources потоки,
                    // а вместе с ними и соединение будут закрыты
                    System.out.println("Client disconnected!");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
