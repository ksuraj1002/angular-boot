package com.springsecuritylocalization.advancedJava.abstraction;

public interface Bank {
	 
	public static final float LOAN_PRINCIPAL_AMOUNT = 0;
	public static final float RATE_OF_INTEREST = 0;
	public static final int DURATION = 0;
	public double calculateEMI(int principalLoanAmount, float interestRate, int duration2);
	
}
