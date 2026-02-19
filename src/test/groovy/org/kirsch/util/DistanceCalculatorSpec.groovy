import org.kirsch.util.DistanceCalculator

class DistanceCalculatorSpec extends CoffeeRideDecoratorSpec {

    def "ApproxCenter"() {
        when:
        def found = DistanceCalculator.approxCenter(p0, p1)

        then:
        found == expected
        0 * _

        where:
        p0                               | p1                               | expected
        CLatLng(0, 0)                    | CLatLng(0, 0)                    | CLatLng(0, 0)
        CLatLng(10, 0)                   | CLatLng(0, -10)                  | CLatLng(5, -5)
        CLatLng(37.420761, -122.081356)  | CLatLng(37.41767, -122.079595)   | CLatLng(37.4192155, -122.0804755)
    }

    def "ApproxCrowDistance"() {
        when:
        def found = DistanceCalculator.approxCrowDistance(p0, p1)

        then:
        found == expected
        0 * _

        where:
        p0                               | p1                               | expected
        CLatLng(0, 0)                    | CLatLng(0, 0)                    | 0
        CLatLng(10, 0)                   | CLatLng(0, -10)                  | 1571348
        CLatLng(37.420761, -122.081356)  | CLatLng(37.41767, -122.079595)   | 395
    }

    def "_ApproxCrowDistance"() {
        when:
        def found = DistanceCalculator._approxCrowDistance(p0, p1)

        then:
        found == expected
        0 * _

        where:
        p0                                              | p1                                               | expected
        CLatLng(0, 0)                                   | CLatLng(0, 0)                                    | 0
        CLatLng(35.66145292768596, -117.6372638312428)  | CLatLng(39.55882733378412, -88.63335914189523)   | 1615.45
        CLatLng(49.48572475342304, -86.26031239458499)  | CLatLng(34.29321860183609, -86.52398425539724)   | 1057.73
        CLatLng(39.96420120463865, -111.13335725965388) | CLatLng(-5.9825189108786265, -38.53570308106527) | 5561.26
        CLatLng(49.14194338968351, 43.20257811218381)   | CLatLng(-19.98919078065685, -64.02398245355315)  | 8040.90
    }

    def "FindNextTarget"() {
        when:
        def found = DistanceCalculator.findNextTarget(p0, p1, 1.0)

        then:
        found == expected
        0 * _

        where:
        p0             | p1              | expected
        CLatLng(0, 0)  | CLatLng(0, 0)   | CLatLng(0, 0)
        CLatLng(10, 0) | CLatLng(0, -10) | CLatLng(9.292893218813452, -0.7071067811865475)
        CLatLng(10, 10) | CLatLng(0, 0) | CLatLng(9.292893218813452, 9.292893218813452)
        CLatLng(10, 10) | CLatLng(0, 10) | CLatLng(9, 10)
    }

    def "ApproxGapSieFromMilesToDegrees"() {
        when:
        def found = DistanceCalculator.approxGapSizeFromMilesToDegrees(p0, p1, 10.0)

        then:
        found == expected
        0 * _

        where:
        p0                                            | p1                                             | expected
        CLatLng(80, 80)                               | CLatLng(0, 80)                                 | 0.014471780028943561
        CLatLng(42.5131088634076, -88.08377988941972) | CLatLng(42.58757147130491, -87.91570340805083) | 0.24253908926
    }

}