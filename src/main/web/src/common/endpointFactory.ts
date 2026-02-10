export class EndpointFactory {
  static getAppServerBaseUrl() {
    return window.location.toString();
  }
}