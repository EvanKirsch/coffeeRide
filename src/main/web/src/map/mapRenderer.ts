export class MapRenderer {

  static map: google.maps.Map;

  async initMap(): Promise<void> {
    const { Map } = await google.maps.importLibrary("maps") as google.maps.MapsLibrary;
    MapRenderer.map = new Map(document.getElementById("map") as HTMLElement, {
      center: { lat: 43.03, lng: -87.90 },
      zoom: 12,
      mapId: 'MY_MAP_ID'
    });
  }

}