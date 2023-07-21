package com.kh.finalProject.service;

import com.kh.finalProject.dto.WeatherDto;
import com.kh.finalProject.entity.Region;
import com.kh.finalProject.entity.Weather;
import com.kh.finalProject.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Value("${weatherApi.serviceKey}")
    private String serviceKey;

    @Scheduled(cron = "0 0 * * * *")
    public void getWeatherDataScheduler() {
        List<WeatherDto> weatherData = getWeatherData("ALL");
    }

    public List<WeatherDto> getWeatherData(String regionList) {

        List<Region> regions = weatherRepository.findAll();
        List<WeatherDto> weatherDtoList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (Region region : regions) {
            try {
                // 2. 요청 시각 조회
                String yyyyMMdd = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                int hour = now.getHour();
                int min = now.getMinute();
                if (min <= 30) {
                    hour -= 1;
                }
                String hourStr = String.format("%02d00", hour); // 정시 기준
                String nx = Integer.toString(region.getNx());
                String ny = Integer.toString(region.getNy());
                String currentChangeTime = now.format(DateTimeFormatter.ofPattern("yy.MM.dd ")) + hour;

                Weather prevWeather = region.getWeather();
                if (prevWeather != null && prevWeather.getLastUpdateTime() != null) {
                    if (prevWeather.getLastUpdateTime().equals(currentChangeTime)) {
                        log.info("기존 자료를 재사용합니다");
                        WeatherDto dto = new WeatherDto();
                        dto.setId(region.getId());
                        dto.setRegion(region.getParentRegion());
                        dto.setTemp(region.getWeather().getTemp());
                        dto.setRainAmount(region.getWeather().getRainAmount());
                        dto.setHumid(region.getWeather().getHumid());
                        dto.setLastUpdateTime(currentChangeTime);

                        weatherDtoList.add(dto);
                    } else { // 라스트업데이트 타임이 일치하지 않는 경우, api 호출
                        WeatherDto dto = requestWeatherData(region, yyyyMMdd, hourStr, nx, ny, currentChangeTime);
                        weatherDtoList.add(dto);
                    }
                } else {
                    // 기존 날씨 정보가 없는 경우, api 호출
                    WeatherDto dto = requestWeatherData(region, yyyyMMdd, hourStr, nx, ny, currentChangeTime);
                    weatherDtoList.add(dto);
                }
            } catch (IOException e) {
                log.error("날씨 정보를 불러오는 중 오류가 발생했습니다.", e);
            }
        }
        return weatherDtoList;
    }

    // api 호출 부분을 메서드
    private WeatherDto requestWeatherData(Region region, String yyyyMMdd, String hourStr, String nx, String ny, String currentChangeTime) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(yyyyMMdd, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(hourStr, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(nx, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(ny, "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        log.info("request url: {}", url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        String data = sb.toString();

        // 응답 완료 후 JSON 파싱
        Double temp = null;
        Double humid = null;
        Double rainAmount = null;

        JSONObject jObject = new JSONObject(data);
        JSONObject response = jObject.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray jArray = items.getJSONArray("item");

        for(int i = 0; i < jArray.length(); i++) {
            JSONObject obj = jArray.getJSONObject(i);
            String category = obj.getString("category");
            double obsrValue = obj.getDouble("obsrValue");

            switch (category) {
                case "T1H":
                    temp = obsrValue;
                    break;
                case "RN1":
                    rainAmount = obsrValue;
                    break;
                case "REH":
                    humid = obsrValue;
                    break;
            }
        }

        // DB에 값 업데이트
        Weather weather = new Weather(temp, rainAmount, humid, currentChangeTime);
        region.updateRegionWeather(weather);
        WeatherDto dto = new WeatherDto();
        dto.setId(region.getId());
        dto.setRegion(region.getParentRegion());
        dto.setTemp(temp);
        dto.setRainAmount(rainAmount);
        dto.setHumid(humid);
        dto.setLastUpdateTime(currentChangeTime);

        return dto;
    }
}
