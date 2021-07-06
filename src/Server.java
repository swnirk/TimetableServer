import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    public static final String fileWay = "C:\\Users\\79651\\Desktop\\timetable.csv";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println(GSON.toJson("Server started!"));

            while (true) {

                // ожидаем подключения
                Socket socket = serverSocket.accept();
                System.out.println(GSON.toJson("Client connected!"));

                // для подключившегося клиента открываем потоки
                // чтения и записи
                try (BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(socket.getOutputStream())) {

                    // ждем первой строки запроса
                    while (!input.ready()) ;

                    // считываем и печатаем запрос клиента, определяем id рейса
                    System.out.println();
                    String request = input.readLine();
                    System.out.println(GSON.toJson(request));
                    String id = request.split("/")[2].split(" ")[0];

                    ConvertCSVtoJson convert = new ConvertCSVtoJson();
                    ArrayList fltArray = convert.readCsvFile(fileWay);
                    JSONArray fltArrayJson = convert.convertJavaObjectToJsonArray(fltArray);

                    // проверяем, есть ли такой id в базе
                    String isIdHere = convert.checkElementInBase("id",id, fltArrayJson);

                    // если id в базе нет, то сообщаем об этом
                    if (isIdHere == null) {
                        output.println(GSON.toJson("HTTP/1.1 404"));
                        output.println("Content-Type: text/html; charset=utf-8");
                        output.println();
                        output.println("<p>ID not found! Please, chek id and enter again.</p>");
                    }

                    // вывод Departure time и Arrival time в соответсвии с id, введенным клиентом
                    for (int i = 0; i < fltArrayJson.length(); i++) {

                            JSONObject fltObj = fltArrayJson.getJSONObject(i);
                            if (fltObj.get("id").equals(id)) {
                                output.println(GSON.toJson("HTTP/1.1 200 OK"));
                                output.println("Content-Type: text/html; charset=utf-8");
                                output.println();
                                output.println("<p>Your flight is: </p>" + fltObj.get("number"));
                                output.println("<p>Departure time is: </p>" + convert.convertStringToDateFormat(fltObj.get("departureTime")));
                                output.println("<p>Arrival time is: </p>" + convert.convertStringToDateFormat(fltObj.get("arrivalTime")));
                            }
                        }
                    output.flush();

                    // по окончанию выполнения блока try-with-resources потоки,
                    // а вместе с ними и соединение будут закрыты
                    System.out.println(GSON.toJson("Client disconnected!"));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
