package org.kirsch.util.distance

import org.kirsch.CoffeeRideDecoratorSpec

class SphereDistanceCalculatorSpec extends CoffeeRideDecoratorSpec {

    SphereDistanceCalculator sdc // testing impl not interface

    def setup() {
        DistanceCalculatorFactory dcf = new SphereDistanceCalculatorFactory()
        sdc = dcf.getCalculator()
    }

    def "ApproxDistance"() {
        when:
        def found = sdc.approxDistance(p0, p1)

        then:
        IsWithinError(found, expected, 0.01)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | (3828.64) * 1609.34
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | (0.0) * 1609.34
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | (1615.45) * 1609.34
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | (1057.73) * 1609.34
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | (5561.26) * 1609.34
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | (8040.90) * 1609.34
    }

    def "HaversineImpl"() {
        when:
        def found = sdc.haversineImpl(p0, p1)

        then:
        IsWithinError(found, expected, 0.03)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | (3828.64) * 1609.34
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | (0.0) * 1609.34
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | (1615.45) * 1609.34
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | (1057.73) * 1609.34
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | (5561.26) * 1609.34
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | (8040.90) * 1609.34
    }

    def "relativeGapDist"() {
        when:
        def found = sdc.relativeGapDist(p0, p1, 1000)

        then:
        IsWithinError(found, expected, 0.03)
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(38.897778, -77.036389)                  | CLatLng(48.858222, 2.2945)                       | 725
        CLatLng(0.0, 0.0)                               | CLatLng(0.0, 0.0)                                | 0      // no vector exists
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | 795
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | 1000
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | 968
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | 977
    }

}