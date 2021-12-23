package md.carpion.challenge.data.network.model;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class LocationsResponse implements Parcelable {

    public final static Creator<LocationsResponse> CREATOR = new Creator<LocationsResponse>() {

        public LocationsResponse createFromParcel(android.os.Parcel in) {
            return new LocationsResponse(in);
        }

        public LocationsResponse[] newArray(int size) {
            return (new LocationsResponse[size]);
        }

    };

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("locations")
    @Expose
    private List<Item> mItems = null;

    protected LocationsResponse(android.os.Parcel in) {
        this.response = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.mItems, (Item.class.getClassLoader()));
    }

    public LocationsResponse() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Item> getLocations() {
        return mItems;
    }

    public void setLocations(List<Item> items) {
        this.mItems = items;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(LocationsResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("response");
        sb.append('=');
        sb.append(((this.response == null) ? "<null>" : this.response));
        sb.append(',');
        sb.append("locations");
        sb.append('=');
        sb.append(((this.mItems == null) ? "<null>" : this.mItems));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result * 31) + ((this.response == null) ? 0 : this.response.hashCode()));
        result = ((result * 31) + ((this.mItems == null) ? 0 : this.mItems.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof LocationsResponse)) {
            return false;
        }
        LocationsResponse rhs = ((LocationsResponse) other);
        return ((Objects.equals(this.response, rhs.response)) && (Objects.equals(this.mItems, rhs.mItems)));
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(response);
        dest.writeList(mItems);
    }

    public int describeContents() {
        return 0;
    }

}