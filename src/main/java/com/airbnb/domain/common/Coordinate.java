package com.airbnb.domain.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Coordinate {

    @Min(-180)
    @Max(180)
    private double longitude;   // 경도

    @Min(-90)
    @Max(90)
    private double latitude;   // 위도

    public static Coordinate of(Point point) {
        return new Coordinate(point.getCoordinate().getX(), point.getCoordinate().getY());
    }
}