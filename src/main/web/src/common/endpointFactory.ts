export class EndpointFactory {
    static getAppServerBaseUrl(){
        return window.location.protocol + "//" + window.location.host + ":8080"
    }
}