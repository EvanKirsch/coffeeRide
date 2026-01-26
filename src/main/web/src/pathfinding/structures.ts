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