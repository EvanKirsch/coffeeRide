import { setOptions } from "@googlemaps/js-api-loader"
import { EndpointFactory } from "./endpointFactory"

export class GMapsApiLoader {

  async configGMapsJsApi() {
    const key : string = await this.getGMapsJsApiKey();
    setOptions({
      key: key,
      v: 'weekly',
    });
  }

  async getGMapsJsApiKey() : Promise<string> {
    return fetch(EndpointFactory.getAppServerBaseUrl() + "gMapsApiLoader",{
      method:"GET",
      headers:{"Content-Type":"application/json"}
    }).then(async (response) => {
      const data : ApiKeyResponse = await response.json()
      return data.key
    });
  }

}