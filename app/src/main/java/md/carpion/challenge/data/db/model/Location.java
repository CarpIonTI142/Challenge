package md.carpion.challenge.data.db.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import md.carpion.challenge.data.network.model.Item;

public class Location extends RealmObject {
    @PrimaryKey
    private String label;
    private double lat;
    private double lng;
    private String address;
    private String image;
    private double lang;

    public Location(Item item) {
        this.label = item.getLabel();
        this.lat = item.getLat();
        this.lng = item.getLng();
        this.address = item.getAddress();
        this.image = item.getImage();
        this.lang = item.getLang();

        if (lng == 0) {
            lng = lang;
        }
    }

    public Location() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }
}
