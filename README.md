```bash
curl -X PUT -d '{
  "origin":"42.680835452638945,-88.28317875190854",
  "destination":"43.03205698379127,-87.91330479781325",
  "step":"0.2"
}' -H 'Content-Type: application/json' localhost:8080/pathfinding
```

Prototype:
![prototype screenshot](https://github.com/EvanKirsch/coffeeRide/blob/master/screenshots/prototype.png)