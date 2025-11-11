package com.springsecuritylocalization.advancedJava.abstraction;

public class MainAbstraction {

	public static void main(String[] args) { 
		int principalLoanAmount = 42138599;
		float interestRate = 7.3f;
		int duration = 8;
		
		Bank sbi = new SBI();
		double emi = sbi.calculateEMI(principalLoanAmount,interestRate,duration);
		System.out.println("EMI is -> "+emi);
	}

}
