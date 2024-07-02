package come

def call(){
    def asd = 'asd'
    def greenSt = '\u001B[32m'
    def redSt = '\u001B[31m'
    def yellowSt = '\u001B[32m'
    def closeCol = '\u001B[0m'
    ansiColor('xtrem'){
        println "${greenSt}Zdraste ERROR${closeCol}"
    }
}