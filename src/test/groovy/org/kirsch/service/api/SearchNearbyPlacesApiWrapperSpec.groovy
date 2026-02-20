package org.kirsch.service.api

import org.kirsch.CoffeeRideDecoratorSpec
import org.kirsch.util.distance.DistanceCalculatorFactory

class SearchNearbyPlacesApiWrapperSpec extends CoffeeRideDecoratorSpec {

    SearchNearbyPlacesApiWrapper snp // testing impl not interface
    DistanceCalculatorFactory dcf

    def setup() {
        dcf = Mock()
        snp = new SearchNearbyPlacesApiWrapper(dcf)
    }

    def "GetLocationRestriction"() {
        when:
        def found = snp.getLocationRestriction(p0, p1)

        then:
        // TODO
        0 * _

        where:
        p0                               | p1
        CLatLng(0, 0)                    | CLatLng(0, 0)
        CLatLng(10, 0)                   | CLatLng(0, -10)
        CLatLng(37.420761, -122.081356)  | CLatLng(37.41767, -122.079595)
    }

}