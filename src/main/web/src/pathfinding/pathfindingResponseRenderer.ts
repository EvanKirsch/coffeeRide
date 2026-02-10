import { MapRenderer } from "../map/mapRenderer"

export class PathfindingResponseRenderer {

  async renderResponse(response) {
    const data: PathfindingResponse = await response.json();
    const { AdvancedMarkerElement } = await google.maps.importLibrary("marker") as google.maps.MarkerLibrary;
    const { Map } = await google.maps.importLibrary("maps") as google.maps.MapsLibrary;
    data.legs.forEach((place) => {
      new AdvancedMarkerElement({
        map: MapRenderer.map,
        position: { lat: place.origin.lat, lng: place.origin.lng },
        title: place.origin.displayName,
      });
    });
    const {encoding} = (await google.maps.importLibrary("geometry")) as google.maps.GeometryLibrary;
    const decodedPath = encoding.decodePath(data.encodedPolyline);
    var polyOptions = {
      path: decodedPath,
      strokeColor: "#FF0000",
      strokeOpacity: 1,
      strokeWeight: 3
    }
    var polyline = new google.maps.Polyline(polyOptions);
    polyline.setMap(MapRenderer.map);
  }

}