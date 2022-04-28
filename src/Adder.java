public class Adder {
    // I - stan poczatkowy, 
    // C - ostatni znak to cyfra, 
    // S - ostatni znak to spacja
    // OK - poprawne zsumowanie, 
    // ERR - wystąpił blad
    private String state; 
    private int sum;
    private int number;

    Adder() {
        state = "I";
        sum = 0;
        number = 0;
    }

    public void resetAdder() {
        state = "I";
        sum = 0;
        number = 0;
    }

    public String read(char[] charBuff, int len) {

        for (int i = 0; i < len; i++) {
            System.out.format("%c", charBuff[i]);
        }

        return "";
    }
}
