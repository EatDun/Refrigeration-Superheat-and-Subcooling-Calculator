public class Main {

    // Adjusts values to PSIA
    public static double psiaAdj(double num) {
        return num + 14.696;
    }

    // Adjusts values from Rankine to Fahrenheit
    public static double rankinAdj(double num) {
        return num - 459.67;
    }

    // Equation to solve saturation pressure from saturation temperature
    public static double ptEquation(double temp, Refrigerant refrigerant) {
        return refrigerant.calculatePTEquation(temp);
    }

    // Returns string/boolean for midpoint approximation
    public static String comparePoints(double num1, double num2) {
        return (num1 < num2) ? "Over" : "Under";
    }

    // Returns the midpoint between two values
    public static double midFinder(double num1, double num2) {
        return (num1 + num2) / 2;
    }

    // Returns the percentage difference of two variables
    public static double errorFind(double num1, double num2) {
        return Math.abs((num1 - num2) / num2) * 100;
    }

    // Subtracts for final value
    public static double finalSub(double num1, double num2) {
        return num1 - num2;
    }

    // Performs bisection
    public static double bisection(double numL, double numR, double targetPress, Refrigerant refrigerant) {
        while (true) {
            double mid = midFinder(numL, numR);
            double calculatedPress = ptEquation(mid, refrigerant);

            if (errorFind(calculatedPress, targetPress) < 0.000000000001) {
                return mid;
            }
            else {
                if ("Over".equals(comparePoints(calculatedPress, targetPress))) {
                    numL = mid;
                } else {
                    numR = mid;
                }
            }    
        }   
    }

    public static void main(String[] args) {

        // Creating scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Ask for refrigerant type
        System.out.println("Enter refrigerant type\nType 1 for R-22\nType 2 for R-410A\nType 3 for R-134A\nType 4 for R-404A\nType 5 for R-407C");
        
        int refType = scanner.nextInt();

        // Validate refrigerant type input
        if (refType < 1 || refType > 5) {
            System.out.println("Invalid refrigerant type.");
            return;
        }

        // Ask for pressure and temperature inputs
        System.out.println("\nEnter Liquid Line Pressure:");
        double liquidLinePress = scanner.nextDouble();

        System.out.println("Enter Liquid Line Temperature:");
        double liquidLineTemp = scanner.nextDouble();

        System.out.println("Enter Vapor Line Pressure:");
        double vaporLinePress = scanner.nextDouble();

        System.out.println("Enter Vapor Line Temperature:");
        double vaporLineTemp = scanner.nextDouble();

        // Adjust inputs to PSIA
        double liquidLinePressAdjPSIA = psiaAdj(liquidLinePress);
        double vaporLinePressAdjPSIA = psiaAdj(vaporLinePress);
        
        // Define refrigerants and refrigerant qualities
        Refrigerant r22 = new Refrigerant("R-22", refType, 707.21, 204.81, 418.33, 664.51, temp -> {
            double A = 29.35754453;
            double B = 3845.193152;
            double C = 7.86103122;
            double D = 0.002190939044;
            double E = 305.8268131;
            double F = 686.1;
            return Math.pow(10, A - B / temp - C * Math.log10(temp) + D * temp + (E * (F - temp)) / (F * temp) * Math.log10(F - temp));
        });

        Refrigerant r410a = new Refrigerant("R-410A", refType, 714.5, 161.83, 408.0, 621.5, temp -> {
            double X = (1 - (temp / 621.5)) - 0.2086902;
            double e = Math.E;
            double A = -1.4376;
            double B = -6.8715;
            double C = -0.53623;
            double D = -3.82642;
            double E = -4.06875;
            double F = -1.2333;
            return (Math.pow(e, (1 / (temp / 621.5)) * (A + (B * X) + (C * Math.pow(X, 2)) + (D * Math.pow(X, 3)) + (E * Math.pow(X, 4)) + (F * Math.pow(X, 5))))) * 714.5;
        });
        
        Refrigerant r134a = new Refrigerant("R-134A", refType, 588.9, 213.9, 444.7, 673.6, temp -> {
            double A = 43.25629;
            double B = -4293.056;
            double C = -13.06883;
            double D = 0.004231114;;
            double E = 0.2342564;
            double F = 677;
            return Math.pow(10, A + B / temp + C * Math.log10(temp) + D * temp + E * ((F - temp) / temp) * Math.log10(F - temp));
        });
        
        Refrigerant r404a = new Refrigerant("R-404A", refType, 541.2, 161.73, 407.7, 621.4, temp -> {
            double e = 2.71828;
            double A = 57.5859;
            double B = -6522.92;
            double C = -6.58061;
            double D = 0.00000394176;
            return Math.pow(e, A + (B / temp) + C * Math.log(temp) + D * (temp * temp));
        });
        
        Refrigerant r407c = new Refrigerant("R-407C", refType, 669.95, 188.13, 416, 647.8, temp -> {
            double e = 2.71828;
            double A = 43.3622;
            double B = -6020.28;
            double C = -4.39387;
            double D = 0.00000212036;
            return Math.pow(e, A + (B / temp) + C * Math.log(temp) + D * (temp * temp));
        });

        // Array to store refrigerants
        Refrigerant[] refrigerants = {null, r22, r410a, r134a, r404a, r407c}; // Indexing matches refrigerant IDs

        // Choose the refrigerant based on user input
        Refrigerant chosenRefrigerant = refrigerants[refType];

        // Perform bisection to calculate saturation temperatures
        double liquidSatR = bisection(chosenRefrigerant.satRLo, chosenRefrigerant.satRHi, liquidLinePressAdjPSIA, chosenRefrigerant);
        double liquidSatF = rankinAdj(liquidSatR);
        double vaporSatR = bisection(chosenRefrigerant.satRLo, chosenRefrigerant.satRHi, vaporLinePressAdjPSIA, chosenRefrigerant);
        double vaporSatF = rankinAdj(vaporSatR);

        // Calculate subcooling and superheat
        double subCooling = finalSub(liquidSatF, liquidLineTemp);
        double superHeat = finalSub(vaporLineTemp, vaporSatF);

        // Display results
        if (subCooling < 0) {
            System.out.println("Subcooling cannot be below zero.");
        } else if (superHeat < 0) {
            System.out.println("Superheat cannot be below zero.");
        } else {
            System.out.println("\nSubcooling: " + subCooling + "\nSuperheat: " + superHeat);
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
