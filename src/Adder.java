import java.util.LinkedList;
import java.util.List;

public class Adder {
    private State state;
    private int sum;
    private StringBuilder number;
    private List<String> results;

    private enum State {
        /**
         *  INIT - stan poczatkowy, 
         * DIGIT - ostatni znak to cyfra, 
         * SPACE - ostatni znak to spacja
         * CR - Carriage return appeard
         * CRERR - Carriage return appeard, error was found 
         * OK - poprawne zsumowanie, 
         * ERR - wystąpił blad
         */
        INIT, DIGIT, SPACE, CR, CRERR, OK, ERR
    }


    Adder() {
        state = State.INIT;
        sum = 0;
        number = new StringBuilder();
        results = new LinkedList<>();
    }

    public void resetAdder() {
        state = State.INIT;
        sum = 0;
        number = new StringBuilder();
    }

    public void calculateSum(char[] charBuff, int len) {

        for (int i = 0; i < len; i++) {
    
            if (canAppendDigit(getCharType(charBuff[i]))) {
                appendDigit(charBuff[i]);

            } else if (canAddNumberToSum(getCharType(charBuff[i]))) {
                addNumber(State.SPACE);

            } else if (didCRAppeard(getCharType(charBuff[i]))) {
                addNumber(State.OK);
                state = getAppropriateCRState();

            } else if (wasQueryCorrect(getCharType(charBuff[i]))) {
                addToResults(Integer.toString(sum));

            } else if (wasQueryIncorrect(getCharType(charBuff[i]))) {
                addToResults("ERROR");
            } else {
                state = State.ERR;
            }
        }

    }
    
    private Adder.State getAppropriateCRState() {
        return (state == State.ERR || state == State.SPACE || state == State.INIT) ? State.CRERR : State.CR;
    }

    private void addToResults(String result) {
        results.add(String.format("%s\r\n",result));
        resetAdder(); 
    }

    public List<String> getResults() {
        return results;
    }

    public void clearResults() {
        results.clear();
    }

    private boolean wasQueryIncorrect(int charType) {
        return (charType == 3 && state != State.CR);
    }

    private boolean wasQueryCorrect(int charType) {
        return state == State.CR && charType == 3;
    }

    private boolean didCRAppeard(int charType) {
        return charType == 2;
    }

    private boolean canAppendDigit(int charType) {
        return (state == State.INIT && charType == 0
            || state == State.DIGIT && charType == 0
            || state == State.SPACE && charType == 0);
    }

    private void appendDigit(char c) {
        number.append(c);
        state = State.DIGIT;
    }

    private boolean canAddNumberToSum(int charType) {
        return (state == State.DIGIT && charType == 1);
    }

    private void addNumber(State nextState) {
        try {
            sum = Math.addExact(sum, Math.toIntExact(Long.valueOf(number.toString())));
            number.setLength(0);
            state = nextState;
        } catch (ArithmeticException ex) {
            state = State.ERR;
        } catch (NumberFormatException ex) {
            state = State.ERR;
        }
    }

    private int getCharType(char c) {

        if (Character.isDigit(c)) return 0;
        if (c == 32) return 1;
        if (c == '\r') return 2;
        if (c == '\n') return 3;

        return -1;
    }
}
