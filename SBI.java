package com.springsecuritylocalization.advancedJava.abstraction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SBI implements Bank {

	@Override
	public double calculateEMI(int principalLoanAmount, float interestRate, int durationInYears) {
		double montlyInterestRate = (interestRate/100.0)/12.0;
		int durationInMonths = durationInYears*12;
		
		double powerFactor = Math.pow((1 + montlyInterestRate), durationInMonths);
	    double emi = (principalLoanAmount * montlyInterestRate * powerFactor) / (powerFactor - 1); 
	    BigDecimal bd = new BigDecimal(emi).setScale(0, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	} 
 
	

}
