import com.google.type.LatLng
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

}
