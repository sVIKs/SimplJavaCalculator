package calc;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorState {
    private static final int STRING_LENGHT = 10;

    private String history;
    private String prevValue;
    private boolean prevInv;
    private String operator;
    private String curValue;
    private boolean curInv;

    public CalculatorState() {
        reset();
    }

    public void reset() {
        this.history = "";
        this.prevValue = "";
        this.prevInv = false;
        this.operator = "";
        this.curValue = "0";
        this.curInv = false;
    }

    public static String removeTailZeros(String input) {
        if (input.contains(".")) {
            while (input.endsWith("0")) {
                input = input.substring(0, input.length() - 1);
            }
            if (input.endsWith(".")) {
                input = input.substring(0, input.length() - 1);
            }
        }
        return input;
    }

    public static String removeTrailingDot(String input) {
        if (input.endsWith(".")) {
            return input.substring(0, input.length() - 1);
        }
        return input;
    }

    public String getExpression() {
        String prVal = convertValue(this.prevValue, this.prevInv);
        String crVal = convertValue(this.curValue, this.curInv);
        if (!prVal.equals("") && this.operator.equals("") && !crVal.equals("")) {
            this.prevValue = "";
            this.prevInv = false;
        }
        String answer = (prVal + " " + this.operator + " " + crVal).trim();

        if (this.operator.equals("") && !this.history.equals("")) {
            return this.history;
        }
        return answer;
    }

    public String convertValue(String val, Boolean inv) {
        if (inv) {
           return "-" + val + ""; 
        }
        return val;
    }

    public String calculate(Boolean continuousFlow) {
        try {
            BigDecimal prvVal = new BigDecimal(convertValue(this.prevValue, this.prevInv));
            BigDecimal curVal = new BigDecimal(convertValue(this.curValue, this.curInv));
            BigDecimal decResult = new BigDecimal("0");
            switch (operator) {
                case "+":
                    decResult = prvVal.add(curVal);
                    break;
                case "-":
                    decResult = prvVal.subtract(curVal);
                    break;
                case "×":
                    decResult = prvVal.multiply(curVal);
                    break;
                case "÷":
                    if (!curVal.equals(BigDecimal.ZERO)) {
                        decResult = prvVal.divide(curVal, MathContext.DECIMAL128); 
                        break;
                    } else {
                        return "NaN";
                    }
                default:
                    return "Undef";
            }
            System.out.println("" + prvVal + operator + curVal + "=" + decResult);

            String val = removeTailZeros(decResult.abs().toString());
            if (val.length()>(STRING_LENGHT*2)) {
                return "NaN";
            }

            Boolean inv = (decResult.compareTo(BigDecimal.ZERO) < 0);

            this.history = getExpression() + " = " + convertValue(val, inv );

            this.operator= "";
            if (continuousFlow) {
                this.prevValue = val;
                this.prevInv = inv;

                this.curValue= "";
                this.curInv = false;
            } else {
                this.prevValue = "";
                this.prevInv = false;

                this.curValue= val;
                this.curInv = inv;
            }
            return "ok";
        } catch (NumberFormatException e) {
            return "Undef";
        }
    }

    public String[] enterCmd(String id, String val) {
        switch (id) {
            case "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine":
                if (this.curValue.equals("0")) {
                    this.curValue = "";
                } 
                if (this.curValue.length() < STRING_LENGHT) {
                    this.curValue = this.curValue + val;
                }
                if (this.operator.equals("")){
                    this.history = "";
                    this.prevValue = "";
                    this.prevInv = false;
                }
                break;
            case "point":
                if (this.curValue.equals("")) {
                    this.curValue = "0";
                }
                if (this.curValue.length() < STRING_LENGHT && !this.curValue.contains(String.valueOf("."))) {
                    this.curValue = this.curValue + val;
                }
                break;
            case "reset":
                reset();
                break;
            case "inversion":
                this.curInv = !this.curInv;
                break;
            case "multiply", "minus", "plus", "divide":
                this.curValue = removeTrailingDot(this.curValue);
                if (!this.operator.equals("") && !this.curValue.equals("") ) {
                    String st = calculate(true);
                    if (st != "ok") {
                        return new String[]{st, getExpression()};
                    }
                } else if (!this.prevValue.equals("")) {
                    this.curValue= "";
                    this.curInv = false;
                } else {
                    this.prevValue = this.curValue;
                    this.prevInv = this.curInv;
                    this.curValue= "";
                    this.curInv = false;
                }
                this.operator = val;
                break;
            case "equals":
                this.curValue = removeTrailingDot(this.curValue);
                if (!this.operator.equals("") && !this.curValue.equals("")) {
                    String st = calculate(false);
                    if (st != "ok") {
                        return new String[]{st, getExpression()};
                    }
                }
                break;
            case "delete":
                if (!this.curValue.equals("")) {
                    this.curValue = this.curValue.substring(0, this.curValue.length() - 1);
                }
                if (this.curValue.equals("")) {
                    this.curInv = false;
                    this.curValue = "0";
                }
                break;
            default:
                System.out.println("[ERROR]Undefined button "+ id + ":" + val);
                return new String[]{"Error", getExpression()};
        }
        String showVal = convertValue(this.curValue, this.curInv);
        if (showVal.equals("")) {
            showVal = convertValue(this.prevValue, this.prevInv);
        } 
        return new String[]{showVal, getExpression()};
    }
}