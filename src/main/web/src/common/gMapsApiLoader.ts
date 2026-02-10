import { setOptions } from "@googlemaps/js-api-loader"

export class GMapsApiLoader {

  configGMapsJsApi() {
    setOptions({
      key: "",
      v: 'weekly',
    });
  }

}