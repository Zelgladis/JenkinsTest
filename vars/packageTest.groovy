import come.*
import fistry.*

def call() {
    mypr = new come.mypro()
    myzal = new come.zaluna()
    fff = new fistry.omg()
    //varsm = new come.VarsM()
    stage('TestPackage') {
        mypr.mypro()
        mypr.ddd()
        myzal()
        fff()
    }
    come.VarsM.ms.each{ v->
        echo v
    }
}
