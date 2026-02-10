import { PathfindingForm } from "./pathfinding/pathfindingForm"
import { MapRenderer } from "./map/mapRenderer"

const mapRenderer = new MapRenderer();
mapRenderer.initMap();

const pathfindingForm = new PathfindingForm();
pathfindingForm.initAutocompleteWidgets();
pathfindingForm.addPathfindingSubmitEvent();