public class Adder {
    private State state;
    private int sum;
    private StringBuilder number;

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
    }

    public void resetAdder() {
        state = State.INIT;
        sum = 0;
        number = new StringBuilder();
    }

    public String getSum(char[] charBuff, int len) {

        for (int i = 0; i < len; i++) {
            // System.out.format("%d ", getCharType(charBuff[i]));
    
            if (canAppendDigit(getCharType(charBuff[i]))) {
                appendDigit(charBuff[i]);

            } else if (canAddNumberToSum(getCharType(charBuff[i]))) {
                addNumber(State.SPACE);

            } else if (didCRAppeard(getCharType(charBuff[i]))) {
                state = state == State.ERR ? State.CRERR : State.CR;

            } else if (wasQueryCorrect(getCharType(charBuff[i]))) {
                addNumber(State.OK);

            } else if (wasQueryIncorrect(getCharType(charBuff[i]))) {

            } else {
                state = State.ERR;
            }

            System.out.format("number:%s sum:%d%n", number, sum);
        }

        return "";

    }
    
    private boolean wasQueryIncorrect(int charType) {
        return state == State.CRERR && charType == 3;
    }

    private boolean wasQueryCorrect(int charType) {
        return state == State.CR && charType == 3;
    }

    private void clean() {
        resetAdder();
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
            sum = Math.addExact(sum,Math.toIntExact(Long.valueOf(number.toString())));
            number.setLength(0);
            state = nextState;
        } catch (ArithmeticException ex) {
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
