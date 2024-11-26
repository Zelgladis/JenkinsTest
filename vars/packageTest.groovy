import come.*
import fistry.*

def call() {
    def err = false
    if(!params.MICROSERVICE_NAME){
        err = true
    }
    properties([
            parameters([choice(name: 'MICROSERVICE_NAME', 
                choices: come.VarsM.ms,
                description: 'description')
            ])
        ])
    if(err){
        error('MICROSERVICE_NAME created')
    }
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
