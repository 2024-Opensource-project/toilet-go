package com.myspring.springmaster.service;

import com.myspring.springmaster.dataAccess.DTO.ToiletDTO;
import com.myspring.springmaster.dataAccess.entity.Toilet;
import com.myspring.springmaster.dataAccess.mapper.ToiletMapper;
import com.myspring.springmaster.dataAccess.repository.ToiletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ToiletService {
    private final ToiletRepository toiletRepository;
    private final NaverMapApi naverMapApi;

    @Autowired
    public ToiletService(ToiletRepository toiletRepository, NaverMapApi naverMapApi) {
        this.toiletRepository = toiletRepository;
        this.naverMapApi = naverMapApi;
    }

    //화장실 정보 DTO로 반환
    public ToiletDTO getToilet(int id){
        Toilet toilet = toiletRepository.findById((long)id).orElse(null);
        return ToiletMapper.Instance.toDTO(toilet);
    }

    public List<ToiletDTO> getToiletsByFilter(ToiletDTO filter, int num) {
        Pageable pageable = PageRequest.of(0, num);
        List<Toilet> toilets = toiletRepository.findAllByFilter(filter);
        List<ToiletDTO> toiletsDTO = new ArrayList<>();
        for (Toilet toilet : toilets) {
            toiletsDTO.add(ToiletMapper.Instance.toDTO(toilet));
        }
        return toiletsDTO;
    }

    public List<ToiletDTO> getToilets(int num) {
        Pageable pageable = PageRequest.of(0, num); // 0번째 페이지에서 num개의 데이터
        Page<Toilet> toilets = toiletRepository.findAll(pageable);

        List<ToiletDTO> rtnValue = new ArrayList<>();
        for (Toilet toilet : toilets.getContent()) { // 페이징된 데이터만 가져옴
            rtnValue.add(ToiletMapper.Instance.toDTO(toilet));
        }
        return rtnValue;
    }

    public Page<ToiletDTO> getToiletsPage(Pageable pageable) {
        Page<Toilet> toilets = toiletRepository.findAll(pageable);
        return toilets.map(ToiletMapper.Instance::toDTO); // Page 객체에 DTO 매핑
    }



//    public List<ToiletDTO> getToilets(int num){
//        Pageable pageable = PageRequest.of(0, num);
//        List<Toilet> toilets = toiletRepository.findAll(pageable).getContent();
//        List<ToiletDTO> rtnValue = new ArrayList<>();
//        for(Toilet toilet : toilets){
//            rtnValue.add(ToiletMapper.Instance.toDTO(toilet));
//        }
//        return rtnValue;
//    }

    //모든 화장실 위치 반환(리스트)
    public List<double[]> getAllToiletsLocation(){
        List<double[]> locationList = new ArrayList<>();
        List<Toilet> t = toiletRepository.findAll();
        List<ToiletDTO> toilets = new ArrayList<>();
        for (Toilet toilet : t){
            toilets.add(ToiletMapper.Instance.toDTO(toilet));
        }
        toilets.forEach(toilet -> {
            double[] location = {toilet.getLatitude().doubleValue(), toilet.getLongitude().doubleValue()};
            locationList.add(location);
        });
        return locationList;
    }

    //근처 화장실 위치
    public List<ToiletDTO> getNearToiletList(String address, int distance){
        List<ToiletDTO> toiletDTOList = new ArrayList<>();
        BigDecimal[] latAndLon = getLatitudeAndLongitude(address);
        List<ToiletDTO> nearToiletDTOList = new ArrayList<>();
        if (latAndLon != null){
            nearToiletDTOList = toiletDTOList.stream()
                    .filter(toilet -> isNear(toilet,latAndLon, distance))
                    .toList();
        }
        return nearToiletDTOList;
    }

    //화장실 정보 저장
    public boolean addToilet(ToiletDTO toiletDTO){
        BigDecimal[] latAndLon = this.getLatitudeAndLongitude(toiletDTO.getAddress());
        if (latAndLon != null){
            toiletDTO.setLatitude(latAndLon[0]);
            toiletDTO.setLongitude(latAndLon[1]);
        }
        Toilet toilet = ToiletMapper.Instance.toEntity(toiletDTO);
        toiletRepository.save(toilet);
        return true;
    }

    //mapview용
    public double[] getLatitudeAndLongitudeAsDouble(String address){
        BigDecimal[] data = this.getLatitudeAndLongitude(address);
        double[] rtnValue = new double[2];
        if(data != null) {
            rtnValue[0] = data[0].doubleValue();
            rtnValue[1] = data[1].doubleValue();
        }
        return rtnValue;
    }
    //지도 API에서 해당 주소의 위도, 경도 받아오기
    private BigDecimal[] getLatitudeAndLongitude(String address){
        return naverMapApi.getLatAndLng(address);
    }
    //근처에 있는지 확인 - DTO랑 좌표 비교
    private boolean isNear(ToiletDTO toiletDTO, BigDecimal[] latAndLon2, int wantedDistance){
        BigDecimal[] latAndLon1 = {toiletDTO.getLatitude(), toiletDTO.getLongitude()};
        return calcDistance(latAndLon1, latAndLon2) <= wantedDistance;
    }
    //불필요한 문자열 제거
    private void removeUselessWord(ToiletDTO toilet){
        String[] uselessWords = {"소재지 :", "지도보기"};
        String address = toilet.getAddress();
        for(String word : uselessWords){
            address = address.replace(word, "");
        }
        toilet.setAddress(address.strip());
    }
    //거리 계산
    private double calcDistance(double[] latAndLon1, double[] latAndLon2){
        final double EARTH_RADIUS = 6371.0;
        double lat1Rad = Math.toRadians(latAndLon1[0]);
        double lon1Rad = Math.toRadians(latAndLon1[1]);
        double lat2Rad = Math.toRadians(latAndLon2[0]);
        double lon2Rad = Math.toRadians(latAndLon2[1]);

        // Haversine 공식 적용
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 지구의 반경을 곱하여 거리 계산
        return EARTH_RADIUS * c;

    }
    private double calcDistance(BigDecimal[] latAndLon1, BigDecimal[] latAndLon2){
        return this.calcDistance(
                Arrays.stream(latAndLon1).mapToDouble(BigDecimal::doubleValue).toArray(),
                Arrays.stream(latAndLon2).mapToDouble(BigDecimal::doubleValue).toArray());
    }

}