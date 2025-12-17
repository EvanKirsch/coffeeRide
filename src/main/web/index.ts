let map: google.maps.Map;
async function initMap(): Promise<void> {
    const { Map } = (await google.maps.importLibrary(
        'maps'
    )) as google.maps.MapsLibrary;
    map = new Map(document.getElementById('map') as HTMLElement, {
        center: { lat: 43.03, lng: -87.90 },
        zoom: 12,
    });
}

type Route = {
  origin: string;
  destination: string;
  step: string;
};

document.getElementById("fcsSubmit")?.addEventListener("click", function(e) {
  console.log("onClick")
  e.preventDefault()
  const route = <Route>({
    origin: (<HTMLInputElement>document.getElementById("origin"))?.value,
    destination: (<HTMLInputElement>document.getElementById("destination"))?.value,
    step: (<HTMLInputElement>document.getElementById("step"))?.value
  })
  console.log(route)
  fetch("http://localhost:8080/pathfinding",{
    method:"PUT",
    headers:{"Content-Type":"application/json"},
    body:JSON.stringify(route)
  }).then(()=>{
    console.log("Post route: " + route)
  })
})

initMap();