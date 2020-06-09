import java.util.Scanner;

public class Converter {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
	System.out.print("Enter base radix: ");
        int base = sc.hasNextInt() ? sc.nextInt() : 0;
        sc.nextLine();
	System.out.print("Enter source number: ");
        String number = sc.hasNextLine() ? sc.nextLine() : "missing";
	System.out.print("Enter final radix: ");
        int radix = sc.hasNextInt() ? sc.nextInt() : 0;
        boolean error = wrongInput(base, number, radix);
        
        if (!error) {
            
            double decimal = base > 0 ? getDecimal(number, base) : 0;
	
	    System.out.print("Result: ");
            
            if (radix == base) {

                System.out.println(number);

            } else if (radix == 10) {

                System.out.println(decimal);

            } else if (radix == 1) {

                System.out.println(decTo1(decimal));

            } else {

                System.out.println(decToRadix(decimal, radix));

            } 
        }
    }
    
    public static boolean wrongInput(int base, String number, int radix) {
        
        if (base < 1 || base > 36) {
        
            System.out.println("Error: Wrong source radix.");
            return true;
            
        }
        
        if (radix < 1 || radix > 36) {
            
            System.out.println("Error: Wrong final radix.");
            return true;
            
        }
        
        int dot = 0;
        
        for (int i = 0; i < number.length(); i++) {
            
            char ch = number.charAt(i);
            
            if (ch < 48 || ch > 57 && ch < 65 || ch > 90 && ch < 97 || ch > 122) {
                
                if (ch == 46 && dot < 1) {
                    
                    dot++;
                    
                } else {
                    
                    System.out.println("Error: Wrong source number.");
                    return true; 
                    
                } 
            }
            
            if (ch < 58 && base < ch - 47 && base != 1) {
                
                System.out.println("Error: Wrong source number.");
                return true; 
                    
            }
            
            if (ch > 64 && ch < 91 && base < ch - 54) {
                
                System.out.println("Error: Wrong source number.");
                return true; 
                    
            }
            
            if (ch > 96 && ch < 123 && base < ch - 86) {
                
                System.out.println("Error: Wrong source number.");
                return true; 
                    
            }
        }
        
        return false;
    }

    public static double getDecimal(String number, int base) {

        String[] parts = number.split("\\.");
        long intPart;
        double fractPart = 0;

        if (base == 10) {

            return Double.parseDouble(number);

        } else if (base == 1) {

            return parts[0].length();

        } else {

            intPart = Long.parseLong(parts[0], base);

            if (parts.length > 1 && !parts[1].equals("0*")) {

                for (int i = 0; i < parts[1].length(); i++) {

                    int digit = parts[1].charAt(i) - 48;

                    if (parts[1].charAt(i) > 64 && parts[1].charAt(i) < 91) {

                        digit = Integer.parseInt(String.valueOf(parts[1].charAt(i)), base);

                    }

                    if (parts[1].charAt(i) > 96 && parts[1].charAt(i) < 123) {

                        digit = Integer.parseInt(String.valueOf(parts[1].charAt(i)), base);

                    }

                    fractPart += (digit / Math.pow(base, i + 1));

                }

                return intPart + fractPart;

            }

            return intPart;

        }
    }

    public static String decTo1(double decimal) {

        StringBuilder num = new StringBuilder();

        for (int i = 0; i < Math.floor(decimal) / 1; i++) {

            num.append(1);

        }

        return num.toString();
    }

    public static String decToRadix(double decimal, int radix) {

        StringBuilder result = new StringBuilder();
        String intPart = Long.toString((long) decimal, radix);
        double decFract = decimal % 1;

        result.append(intPart);
        result.append(".");

        for (int i = 0; i < 5; i++) {

            result.append(Long.toString( (long) (decFract * radix / 1), radix));
            decFract = (decFract * radix) % 1;

        }

        return result.toString();
    }
}
