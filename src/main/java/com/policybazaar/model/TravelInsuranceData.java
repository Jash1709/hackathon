package com.policybazaar.model;

public class TravelInsuranceData {
    
    private String country;
    private String startDate;
    private String endDate;
    private int travellerCount;
    private int traveller1Age;
    private int traveller2Age;
    private boolean hasMedicalCondition;
    private String testCaseName;
    
    // Default constructor
    public TravelInsuranceData() {
    }
    
    // Constructor with all fields
    public TravelInsuranceData(String country, String startDate, String endDate, 
                              int travellerCount, int traveller1Age, int traveller2Age, boolean hasMedicalCondition) {
        
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.travellerCount = travellerCount;
        this.traveller1Age = traveller1Age;
        this.traveller2Age = traveller2Age;
        this.hasMedicalCondition = hasMedicalCondition;
    }
    
    // Getters and Setters
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public int getTravellerCount() {
        return travellerCount;
    }
    
    public void setTravellerCount(int travellerCount) {
        this.travellerCount = travellerCount;
    }
    
    public int getTraveller1Age() {
        return traveller1Age;
    }
    
    public void setTraveller1Age(int traveller1Age) {
        this.traveller1Age = traveller1Age;
    }
    
    public int getTraveller2Age() {
        return traveller2Age;
    }
    
    public void setTraveller2Age(int traveller2Age) {
        this.traveller2Age = traveller2Age;
    }
    
    public boolean isHasMedicalCondition() {
        return hasMedicalCondition;
    }
    
    public void setHasMedicalCondition(boolean hasMedicalCondition) {
        this.hasMedicalCondition = hasMedicalCondition;
    }
    
    public String getTestCaseName() {
        return testCaseName;
    }
    
    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }
    
    @Override
    public String toString() {
        return "TravelInsuranceData{" +
                "testCaseName='" + testCaseName + '\'' +
                ", country='" + country + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", travellerCount=" + travellerCount +
                ", traveller1Age=" + traveller1Age +
                ", traveller2Age=" + traveller2Age +
                ", hasMedicalCondition=" + hasMedicalCondition +
                '}';
    }
} 