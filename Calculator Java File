import java.util.Scanner;
import java.lang.Math;

class main {
    
    	//Adjusts values to PSIA
	public static double psiaAdj(double num) {
    	double add = num + 14.696;
    	return add;
	}
    
    	//Adjusts values from rankin to fahrenheit
	public static double rankinAdj(double num) {
    	double sub = num - 459.67;
    	return sub;
	}
   	 
    	//Equation to solve saturation pressure from saturation temperature
	public static double ptEquation(double num, int id, double press, double temp) {
    	if(id == 1){
    	    double A = 29.35754453;
    	    double B = 3845.193152;
    	    double C = 7.86103122;
    	    double D = 0.002190939044;
    	    double E = 305.8268131;
    	    double F = 686.1;
        	double pt = Math.pow(10, A - B / num - C * Math.log10(num)  + D * num + (E * (F - num)) / (F * num) * Math.log10(F - num));
        	return pt;
    	}
    	else if (id == 2) {
        	double X = (1 - (num / temp)) - 0.2086902;
        	double e = 2.71828;
        	double A = -1.4376;
        	double B = -6.8715;
        	double C = -0.53623;
        	double D = -3.82642;
        	double E = -4.06875;
        	double F = -1.2333;
        	double pt = Math.pow(e, (1 / (num / temp)) * (A + (B * X) + (C * Math.pow(X, 2)) + (D * Math.pow(X, 3)) + (E * Math.pow(X, 4)) + (F * Math.pow(X, 5))));
        	return pt * press;
    	}
    	else if (id == 3) {
    	    double A = 43.25629;
            double B = -4293.056 ;
            double C = -13.06883;
            double D = .004231114;
            double E = .2342564;
            double F = 677;
            double pt = Math.pow(10, A + B / num + C * Math.log10(num) + D * num + E * ((F - num) / num) * Math.log10(F - num));
            return pt;
    	}
    	else if (id == 4) {
    	    double e = 2.71828;
    	    double A = 57.5859;
    	    double B = -6522.92;
    	    double C = -6.58061;
    	    double D = .00000394176;
    	    double pt = Math.pow(e, A + (B / num) + C * Math.log(num) + D * (num * num));
    	    return pt;
    	}
    	else if (id == 5) {
    	    double e = 2.71828;
    	    double A =  43.3622;
    	    double B = -6020.28;
    	    double C = -4.39387;
    	    double D = 0.00000212036;
    	    double pt = Math.pow(e, A + (B / num) + C * Math.log(num) + D * (num * num));
    	    return pt;
    	}
    	else {
    	    System.out.println("RefID not found");
    	    return 69;
    	}
	}
   	 
    	//Returns string/boolean for midpoint approximation
	public static String comparePoints(double num1, double num2) {
    	if (num1 < num2) {
        	return "Over";
    	}
    	else {
        	return "Under";
    	}
	}
    
    	//Returns the midpoint between two values
	public static double midFinder(double num1, double num2) {
    	double mid = (num1 + num2) / 2;
    	return mid;
	}
    
    	//Returns the percentage difference of two variables
	public static double errorFind(double num1, double num2) {
    	return Math.abs((num1 - num2) / num2) * 100;
	}
    
    	//Subtracts for final value
	public static double finalSub(double num1, double num2) {
    	return num1 - num2;
	}
    
    	//Performs bisection
	public static double bisection(double numL, double numR, double numSolve, int refID, double press, double temp) {
    	while(true) {
        	if(errorFind(ptEquation(midFinder(numL, numR), refID, press, temp), numSolve) < .000000000001) {
            	return midFinder(numL, numR);
        	}
        	else {
            	if(comparePoints(ptEquation(midFinder(numL, numR), refID, press, temp), numSolve) == "Over") {
                	numL = midFinder(numL, numR);
            	}
            	else if(comparePoints(ptEquation(midFinder(numL, numR), refID, press, temp), numSolve) == "Under") {
                	numR = midFinder(numL, numR);
            	}
        	}
    	}
	}
   	 
	public static void main(String[] args) {
   	 
        	//Creating a scanner for user input
    	Scanner myObj1 = new Scanner(System.in);
   	 
        	//Asking for refrigerant type
   	System.out.println("Enter refrigerant type\nType 1 for R-22\nType 2 for R-410A\nType 3 for R-134a\nType 4 for R-404A\nType 5 for R-407C\n");
   	 
        	//Assigning variables to user input
    	int refType = myObj1.nextInt();
   	 
        	//Array that stores constant values
    	double[] maxPress = {0, 707.21, 714.5, 588.9, 541.2, 669.95};
    	double[] maxTemp = {0, 204.81, 161.83, 213.9, 161.73, 188.13};
   	 
        	//Creating a scanner for user input
    	Scanner myObj2 = new Scanner(System.in);
    
        	//Asking for user input
    	if(refType < 1 || refType > 5) {
        	System.out.println("Refrigerant input not found");
    	}
    	else {
    	System.out.println("\nEnter Liquid Line Pressure\nEnter Liquid Line Temperature\nEnter Vapor Line Pressure\nEnter Vapor Line Temperature\n");
    	}

        	//Assigning variables to user input
    	double liquidLinePress = myObj2.nextDouble();
    	double liquidLineTemp = myObj2.nextDouble();
    	double vaporLinePress = myObj2.nextDouble();
    	double vaporLineTemp = myObj2.nextDouble();
   	 
    	if(liquidLinePress > maxPress[refType] || vaporLinePress > maxPress[refType]) {
        	System.out.println("Gauge reading is above critical pressure");
    	}
    	else if(liquidLinePress < 0 || vaporLinePress < 0) {
        	System.out.println("Charged system pressure cannot be below zero PSIG");
    	}
    	else if(liquidLineTemp > maxTemp[refType] || vaporLineTemp > maxTemp[refType]) {
        	System.out.println("Thermometer reading is above critical temperature");
    	}
    
           	//Adjusting inputs
    	double liquidLinePressAdjPSIA = psiaAdj(liquidLinePress);
    	double vaporLinePressAdjPSIA = psiaAdj(vaporLinePress);
   	 
        	//Math variables
    	double[] RLos = {0, 418.3339585682146, 408, 444.7, 407.7, 416};
    	double[] RHis = {0, 664.5086257144121, 621.5, 673.6, 621.4, 647.8};
    	double satRLo = RLos[refType];
    	double satRHi = RHis[refType];
    	double liquidSatR = bisection(satRLo, satRHi, liquidLinePressAdjPSIA, refType, maxPress[refType], satRHi);
    	double liquidSatF = rankinAdj(liquidSatR);
    	double vaporSatR = bisection(satRLo, satRHi, vaporLinePressAdjPSIA, refType, maxPress[refType], satRHi);
    	double vaporSatF = rankinAdj(vaporSatR);
    	double subCooling = finalSub(liquidSatF, liquidLineTemp);
    	double superHeat = finalSub(vaporLineTemp, vaporSatF);
       	 
        	//Giving results
    	if (subCooling < 0) {
        	System.out.println("Subcooling cannot be below zero");
        }
    	else if(superHeat < 0) {
        	System.out.println("Superheat cannot be below zero");
    	}
    	else {
    	System.out.println("\nSubcooling is " + subCooling + "\nSuperheat is " + superHeat);
    	}
	}
}
//Created with help from,
//Andrew Pansulla and the Chemours Company
//JJ Martin of the University of Michigan
//Larry David of Washtenaw Community College
//Nirmal Patel of Learn Sense
//Brandon White
//Strider Toll
//Josh Levine of Josh.com
//ChatGPT of OpenAI
