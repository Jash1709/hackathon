package com.policybazaar.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Model class representing an insurance plan
 */
public class InsurancePlan {
    
    private static final Logger logger = LogManager.getLogger(InsurancePlan.class);
    
    private String planName;
    private String companyName;
    private double price;
    
    /**
     * Constructor for InsurancePlan
     * @param planName name of the insurance plan
     * @param companyName name of the insurance company
     * @param price price of the insurance plan
     */
    public InsurancePlan(String planName, String companyName, double price) {
        logger.debug("Creating new InsurancePlan - Plan: {}, Company: {}, Price: {}", planName, companyName, price);
        this.planName = planName;
        this.companyName = companyName;
        this.price = price;
        logger.debug("InsurancePlan created successfully");
    }
    
    /**
     * Get plan name
     * @return plan name
     */
    public String getPlanName() {
        logger.debug("Getting plan name: {}", planName);
        return planName;
    }
    
    /**
     * Set plan name
     * @param planName plan name to set
     */
    public void setPlanName(String planName) {
        logger.debug("Setting plan name from '{}' to '{}'", this.planName, planName);
        this.planName = planName;
    }
    
    /**
     * Get company name
     * @return company name
     */
    public String getCompanyName() {
        logger.debug("Getting company name: {}", companyName);
        return companyName;
    }
    
    /**
     * Set company name
     * @param companyName company name to set
     */
    public void setCompanyName(String companyName) {
        logger.debug("Setting company name from '{}' to '{}'", this.companyName, companyName);
        this.companyName = companyName;
    }
    
    /**
     * Get price
     * @return price
     */
    public double getPrice() {
        logger.debug("Getting price: {}", price);
        return price;
    }
    
    /**
     * Set price
     * @param price price to set
     */
    public void setPrice(double price) {
        logger.debug("Setting price from {} to {}", this.price, price);
        this.price = price;
    }
    
    /**
     * String representation of the InsurancePlan
     * @return string representation
     */
    @Override
    public String toString() {
        String result = "InsurancePlan{" +
                "planName='" + planName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", price=" + price +
                '}';
        logger.debug("Converting InsurancePlan to string: {}", result);
        return result;
    }
    
    /**
     * Check if two InsurancePlan objects are equal
     * @param obj object to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            logger.debug("Comparing InsurancePlan with same reference - returning true");
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            logger.debug("Comparing InsurancePlan with null or different class - returning false");
            return false;
        }
        
        InsurancePlan that = (InsurancePlan) obj;
        boolean isEqual = Double.compare(that.price, price) == 0 &&
                planName.equals(that.planName) &&
                companyName.equals(that.companyName);
        
        logger.debug("Comparing InsurancePlan objects - result: {}", isEqual);
        return isEqual;
    }
    
    /**
     * Get hash code for the InsurancePlan
     * @return hash code
     */
    @Override
    public int hashCode() {
        int result = planName.hashCode();
        result = 31 * result + companyName.hashCode();
        result = 31 * result + Double.hashCode(price);
        logger.debug("Generated hash code for InsurancePlan: {}", result);
        return result;
    }
} 