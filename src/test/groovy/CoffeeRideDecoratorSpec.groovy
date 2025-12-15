import com.google.type.LatLng
import spock.lang.Specification

class CoffeeRideDecoratorSpec extends Specification {

    def "CLatLng"(double lat, double lng) {
        return LatLng.newBuilder()
                .setLatitude(lat)
                .setLongitude(lng)
                .build()
    }

}
