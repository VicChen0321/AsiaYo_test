# AsiaYo Test
- 有包了 Docker，可以直接通過 `sh startup.sh` build image run container
- 伺服器會使用 `8080 Port`
- 可以直接 `curl http://localhost:8080/exchangeCurrency?source=TWD&target=USD&amount=2000` 測試
- 單元測試請參考 `src/test/jav/com/asiayo/api/controller/AsiaYoControllerTest`