package come

class VarsM {
    static String greet = 'Hello'
    static String greenSt = '\u001B[32m'
    static String redSt = '\u001B[31m'
    static String yellowSt = '\u001B[32m'
    static String closeCol = '\u001B[0m'
    static String greeting = 'Hello World!'
    public String tea
    static List ms = [
        'ms1',
        'ms2',
        'ms3',
        'ms4',
        'ms5',
        'ms6',
        'ms0'
    ].sort()

    VarsM(){
        this.tea = 'test'
    }

    static void printAsd() {
        echo greet
    }
}