import { EndpointFactory } from "../common/endpointFactory"
import { PathfindingResponseRenderer } from "./pathfindingResponseRenderer"

export class PathfindingForm {

  async initAutocompleteWidgets() {
    (await google.maps.importLibrary('places')) as google.maps.PlacesLibrary;

    const originPlaceAutocomplete = new google.maps.places.PlaceAutocompleteElement(
        {}
    );
    originPlaceAutocomplete.setAttribute("id", "origin")
    document.getElementById("origin")?.replaceWith(originPlaceAutocomplete);

    const destPlaceAutocomplete = new google.maps.places.PlaceAutocompleteElement(
        {}
    );
    destPlaceAutocomplete.setAttribute("id", "destination")
    document.getElementById("destination")?.replaceWith(destPlaceAutocomplete);
  }

  addPathfindingSubmitEvent() {
    document.getElementById("fcsSubmit")?.addEventListener("click", function(e) {
      e.preventDefault()
      const route = <Route>({
        origin: (<HTMLInputElement>document.getElementById("origin"))?.value,
        destination: (<HTMLInputElement>document.getElementById("destination"))?.value,
        step: (<HTMLInputElement>document.getElementById("step"))?.value
      })
      fetch(EndpointFactory.getAppServerBaseUrl() + "pathfinding",{
        method:"PUT",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(route)
      }).then(async (response) => {
        (new PathfindingResponseRenderer()).renderResponse(response)
      })
    })
  }

}