import come.*
import fistry.*

def call() {
    mypr = new come.mypro()
    myzal = new come.zaluna()
    fff = new fistry.omg()
    varsm = new come.VarsM.()
    stage('TestPackage') {
        mypr.mypro()
        mypr.ddd()
        myzal()
        fff()
    }
    echo come.VarsM.greet
    echo varsm.greet
}
