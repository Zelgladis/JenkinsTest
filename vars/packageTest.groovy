import come.*
import fistry.*

def call() {
    mypr = new come.mypro()
    myzal = new come.zaluna()
    fff = new fistry.omg()
    varsm = new come.varsM()
    stage('TestPackage') {
        mypr.mypro()
        mypr.ddd()
        myzal()
        fff()
    }
    echo come.varsM.greet
    echo varsm.greet
}
