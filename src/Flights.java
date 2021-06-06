import org.json.JSONObject;

public class Flights {

    private String id;
    private String origin;
    private String destination;
    private String departureDate;
    private String departureTime;
    private String arrivalDate;
    private String arrivalTime;
    private String number;

    public Flights(String id, String origin, String destination, String departureDate, String departureTime, String arrivalDate, String arrivalTime, String number) {

        this.id = id;
        this.origin = origin;
        this.destination =destination;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String  getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public Flights getFlight(String response) {

        JSONObject fltJson = new JSONObject(response);
        String id = fltJson.getString("Id");
        String origin = fltJson.getString("Origin");
        String destination = fltJson.getString("Destination");
        String departureDate = fltJson.getString("DepartureDate");
        String departureTime = fltJson.getString("DepartureTime");
        String arrivalDate = fltJson.getString("ArrivalDate");
        String arrivalTime = fltJson.getString("ArrivalTime");
        String number = fltJson.getString("Number");

        return new Flights(id,origin,destination,departureDate,departureTime,arrivalDate,arrivalTime,number);
    }
}
