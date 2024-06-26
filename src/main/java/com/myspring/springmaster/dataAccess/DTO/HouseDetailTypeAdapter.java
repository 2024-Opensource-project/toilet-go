package com.myspring.springmaster.dataAccess.DTO;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.myspring.springmaster.dataAccess.entity.HouseDetail;

import java.io.IOException;
import java.lang.reflect.Field;

public class HouseDetailTypeAdapter extends TypeAdapter<HouseDetail> {

    @Override
    public void write(JsonWriter out, HouseDetail houseDetail) throws IOException {
        out.beginObject();
        try {
            Field idField = HouseDetail.class.getDeclaredField("id");
            idField.setAccessible(true);
            out.name("id").value((Integer) idField.get(houseDetail));

            Field typeField = HouseDetail.class.getDeclaredField("type");
            typeField.setAccessible(true);
            out.name("type").value((String) typeField.get(houseDetail));

            Field sizeField = HouseDetail.class.getDeclaredField("size");
            sizeField.setAccessible(true);
            out.name("size").value((String) sizeField.get(houseDetail));

            Field supplyCountField = HouseDetail.class.getDeclaredField("supplyCount");
            supplyCountField.setAccessible(true);
            out.name("supplyCount").value((Integer) supplyCountField.get(houseDetail));

            Field depositField = HouseDetail.class.getDeclaredField("deposit");
            depositField.setAccessible(true);
            out.name("deposit").value((Integer) depositField.get(houseDetail));

            Field monthlyRentField = HouseDetail.class.getDeclaredField("monthlyRent");
            monthlyRentField.setAccessible(true);
            out.name("monthlyRent").value((Integer) monthlyRentField.get(houseDetail));

            Field imageUrlField = HouseDetail.class.getDeclaredField("imageUrl");
            imageUrlField.setAccessible(true);
            out.name("imageUrl").value((String) imageUrlField.get(houseDetail));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        out.endObject();
    }

    @Override
    public HouseDetail read(JsonReader in) {
        HouseDetail houseDetail = new HouseDetail();
        return houseDetail;
    }
}