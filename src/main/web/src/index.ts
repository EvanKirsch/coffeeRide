import { PathfindingForm } from "./pathfinding/pathfindingForm"
import { MapRenderer } from "./map/mapRenderer"
import { GMapsApiLoader } from "./common/gMapsApiLoader"

const gMapsApiLoader = new GMapsApiLoader();
await gMapsApiLoader.configGMapsJsApi();

const mapRenderer = new MapRenderer();
mapRenderer.initMap();

const pathfindingForm = new PathfindingForm();
pathfindingForm.initAutocompleteWidgets();
pathfindingForm.addPathfindingSubmitEvent();