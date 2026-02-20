package org.kirsch

import com.google.type.LatLng
import spock.lang.Specification

class CoffeeRideDecoratorSpec extends Specification {

    def "CLatLng"(double lat, double lng) {
        return LatLng.newBuilder()
                .setLatitude(lat)
                .setLongitude(lng)
                .build()
    }

    def "IsWithinError"(double n0, double n1, double percentError) {
        n0 == 0 ? n0 = Double.MIN_VALUE : _ // prevent 0
        n1 == 0 ? n1 = Double.MIN_VALUE : _ // prevent 0
        return Math.abs(1 - (n0 / n1)) <= percentError
    }

}