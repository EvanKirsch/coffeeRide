package org.kirsch.util.distance

import org.kirsch.CoffeeRideDecoratorSpec

class DistanceCalculatorSpec extends CoffeeRideDecoratorSpec {

    DistanceCalculator dc // testing impl not interface

    def setup() {
        DistanceCalculatorFactory dcf = new FlatDistanceCalculatorFactory()
        dc = dcf.getCalculator()
    }

    def "ApproxCenter"() {
        when:
        def found = dc.getCenter(p0, p1)

        then:
        found == expected
        0 * _

        where:
        p0                               | p1                               | expected
        CLatLng(0, 0)                    | CLatLng(0, 0)                    | CLatLng(0, 0)
        CLatLng(10, 0)                   | CLatLng(0, -10)                  | CLatLng(5, -5)
        CLatLng(37.420761, -122.081356)  | CLatLng(37.41767, -122.079595)   | CLatLng(37.4192155, -122.0804755)
    }

    def "FindNextTarget"() {
        when:
        def found = dc.findNextTarget(p0, p1, 1.0)

        then:
        found == expected
        0 * _

        where:
        p0              | p1              | expected
        CLatLng(0, 0)   | CLatLng(0, 0)   | CLatLng(0, 0)
        CLatLng(10, 0)  | CLatLng(0, -10) | CLatLng(9.292893218813452, -0.7071067811865475)
        CLatLng(10, 10) | CLatLng(0, 0)   | CLatLng(9.292893218813452, 9.292893218813452)
        CLatLng(10, 10) | CLatLng(0, 10)  | CLatLng(9, 10)
    }


    def "ApproxGapSieFromMilesToDegrees"() {
        when:
        def found = dc.approxGapSizeFromMilesToDegrees(p0, p1, 10.0)

        then:
        found == expected
        0 * _

        where:
        p0                                            | p1                                             | expected
        CLatLng(80, 80)                               | CLatLng(0, 80)                                 | 0.014471780028943561
        CLatLng(42.5131088634076, -88.08377988941972) | CLatLng(42.58757147130491, -87.91570340805083) | 0.24253908926
    }

}
