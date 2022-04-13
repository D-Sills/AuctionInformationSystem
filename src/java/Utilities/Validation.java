package Utilities;

/**
 * @author Darren Sills
 * Class containing a number of methods used for validation on entered data
 */
public class Validation {
    /**
     * Checks if the given string is 7 numbers followed by 2 letters
     * @param price generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validPrice(String price) {
        try {
            Double.parseDouble(price);
        }
        catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the given string contains an '@' and '.'
     * @param email generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validEmail(String email) {
        return (email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"));
    }

    /**
     * Checks if the given string contains a valid phone number xxx xxx xxxx
     * @param phone generic string to be validated
     * @return true if valid, else returns false
     */
    public static boolean validPhone(String phone) {
        return (phone.matches("^(\\d{3}[- .]?){2}\\d{4}$"));
    }
}