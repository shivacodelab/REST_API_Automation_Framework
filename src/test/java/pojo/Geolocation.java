package pojo;

public class Geolocation {
	//variables
	private String lat;
    private String lng; // 'long' is reserved keyword

    // Constructor
    public Geolocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
    
    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongValue() {
        return lng;
    }

    public void setLongValue(String lng) {
        this.lng = lng;
    }
}
