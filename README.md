```bash
curl -X PUT -d '{
  "orgLat":42.680835452638945,
  "orgLng":-88.28317875190854,
  "dstLat":43.03205698379127,
  "dstLng":-87.91330479781325,
  "step":0.2
}' -H 'Content-Type: application/json' localhost:8080/pathfinding
```