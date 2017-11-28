package com.navis.insightserver.dto;

import com.navis.insightserver.entity.TagEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by darrell-shofstall on 8/11/17.
 */
public class GuestStayDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String checkIn;
    private String checkOut;
    private String language;
    private String accountNumber;
    private String country;
    private String tripAdvisorId;
    private String unitId;
    private String roomTypeCode;
    private String marketSegment;
    private BigDecimal averageDailyRate;
    private BigDecimal amountPaid;
    private BigDecimal ancillaryPaid;
    private String geoMarketArea;
    private Integer totalStays;

    public GuestStayDTO() {
    }

    public GuestStayDTO(Object guestStay) {

        LinkedHashMap entry = (LinkedHashMap) guestStay;
        setFirstName((String) entry.get("FirstName"));
        setLastName((String) entry.get("LastName"));
        setLanguage((String) entry.get("Language"));
        setAccountNumber((String) entry.get("AccountNumber"));
        setCountry((String) entry.get("Country"));
        setTripAdvisorId((String) entry.get("TripAdvisorId"));
        setUnitId((String) entry.get("UnitId"));
        setRoomTypeCode((String) entry.get("RoomTypeCode"));
        setMarketSegment((String) entry.get("MarketSegment"));
        setGeoMarketArea((String) entry.get("GeoMarketArea"));
        setCheckIn((String) entry.get("CheckIn"));
        setCheckIn((String) entry.get("CheckIn"));
        setTotalStays((Integer) entry.get("TotalStays"));
        setAverageDailyRate(convertDoubleToBigDecimal((Double) entry.get("AverageDailyRate")));
        setAmountPaid(convertDoubleToBigDecimal((Double) entry.get("AmountPaid")));
        setAncillaryPaid(convertDoubleToBigDecimal((Double) entry.get("AncillaryPaid")));

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getLanguage() {
        return language;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCountry() {
        return country;
    }

    public String getTripAdvisorId() {
        return tripAdvisorId;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public String getMarketSegment() {
        return marketSegment;
    }

    public BigDecimal getAverageDailyRate() {
        return averageDailyRate;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public BigDecimal getAncillaryPaid() {
        return ancillaryPaid;
    }

    public String getGeoMarketArea() {
        return geoMarketArea;
    }

    public Integer getTotalStays() {
        return totalStays;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTripAdvisorId(String tripAdvisorId) {
        this.tripAdvisorId = tripAdvisorId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public void setMarketSegment(String marketSegment) {
        this.marketSegment = marketSegment;
    }

    public void setAverageDailyRate(BigDecimal averageDailyRate) {
        this.averageDailyRate = averageDailyRate;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setAncillaryPaid(BigDecimal ancillaryPaid) {
        this.ancillaryPaid = ancillaryPaid;
    }

    public void setGeoMarketArea(String geoMarketArea) {
        this.geoMarketArea = geoMarketArea;
    }

    public void setTotalStays(Integer totalStays) {
        this.totalStays = totalStays;
    }

    private BigDecimal convertDoubleToBigDecimal(Double value) {
        BigDecimal bigDecimalValue = BigDecimal.valueOf(value);
        return bigDecimalValue;
    }
}
