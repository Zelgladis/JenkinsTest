import come.mypro

def call() {
    stage('TestPackage') {
        myproInstance = new mypro()
        myproInstance.mypro()
    }
}
