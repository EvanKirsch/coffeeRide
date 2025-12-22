let map: google.maps.Map;

async function initMap(): Promise<void> {
    const { Map } = (await google.maps.importLibrary("maps")) as google.maps.MapsLibrary;
    map = new Map(document.getElementById("map") as HTMLElement, {
        center: { lat: 43.03, lng: -87.90 },
        zoom: 12,
        mapId: 'MY_MAP_ID'
    });
}

type Route = {
  origin: string;
  destination: string;
  step: string;
};

type CoffeeRidePlace = {
  displayName: string;
  address: string;
  lat: number;
  lng: number;
  name: string;
};

type CoffeeRideLeg = {
  origin: CoffeeRidePlace;
  destination: CoffeeRidePlace;
  encodedPolyline: string;
}

type PathfindingResponse = {
  legs: CoffeeRideLeg[];
  encodedPolyline: string;
};

async function renderResponse(response) {
  const data: PathfindingResponse = await response.json();
  const { AdvancedMarkerElement } = await google.maps.importLibrary("marker") as google.maps.MarkerLibrary;
  data.legs.forEach((place) => {
    new AdvancedMarkerElement({
      map: map,
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
  polyline.setMap(map);
}

document.getElementById("fcsSubmit")?.addEventListener("click", function(e) {
  e.preventDefault()
  const route = <Route>({
    origin: (<HTMLInputElement>document.getElementById("origin"))?.value,
    destination: (<HTMLInputElement>document.getElementById("destination"))?.value,
    step: (<HTMLInputElement>document.getElementById("step"))?.value
  })
  fetch("http://localhost:8080/pathfinding",{
    method:"PUT",
    headers:{"Content-Type":"application/json"},
    body:JSON.stringify(route)
  }).then(async (response) => {
    renderResponse(response)
  })
})

initMap();