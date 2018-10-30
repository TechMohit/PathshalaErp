package in.varadhismartek.pathshalaerp.AddStudent;


public class TranportLatLongDataModel {

    String latLng, origin, stop_name , stop_number , stop_time;
    String routeNode;
    private String trasnport_fees;
    private String dest;
    String stop_distance;

    String assit_name , busname , busno, caretaker_name , destiny , driver_mobno,
    driver_name , routeno, seating , starting , stop_counts , trspt_mgr_mobno,
    trspt_mgr_name;



    TranportLatLongDataModel(){

    }



    public TranportLatLongDataModel(String routeNode, String latLng, String stop_name, String stop_number, String trasnport_fees, String dest, String stop_distance,
                                    String busno , String driver_name , String driver_mobno , String trspt_mgr_name , String trspt_mgr_mobno){
        this.latLng = latLng;
        this.stop_name = stop_name;
        this.routeNode = routeNode;
        this.stop_number = stop_number;
        this.trasnport_fees=trasnport_fees;
        this.dest=dest;
        this.stop_distance = stop_distance;
        this.busno = busno;
        this.driver_name = driver_name;
        this.driver_mobno = driver_mobno;
        this.trspt_mgr_name = trspt_mgr_name;
        this.trspt_mgr_mobno = trspt_mgr_mobno;




    }

    public String getAssit_name() {
        return assit_name;
    }

    public String getBusname() {
        return busname;
    }

    public String getBusno() {
        return busno;
    }

    public String getCaretaker_name() {
        return caretaker_name;
    }

    public String getDestiny() {
        return destiny;
    }

    public String getDriver_mobno() {
        return driver_mobno;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getRouteno() {
        return routeno;
    }

    public String getSeating() {
        return seating;
    }

    public String getStarting() {
        return starting;
    }

    public String getStop_counts() {
        return stop_counts;
    }

    public String getTrspt_mgr_mobno() {
        return trspt_mgr_mobno;
    }

    public String getTrspt_mgr_name() {
        return trspt_mgr_name;
    }

    public String getTrasnport_fees() {
        return trasnport_fees;
    }

    public String getRouteNode() {
        return routeNode;
    }

    public String getDest() {
        return dest;
    }

    public String getLatLng() {
        return latLng;
    }

    public String getOrigin() {
        return origin;
    }

    public String getStop_distance() {
        return stop_distance;
    }

    public String getStop_name() {
        return stop_name;
    }

    public String getStop_number() {
        return stop_number;
    }

    public String getStop_time() {
        return stop_time;
    }
}
