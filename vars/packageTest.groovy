import come.mypro

def call() {
    stage('TestPackage') {
        myproInstance = new come.mypro()
        myproInstance.mypro()
        myproInstance.ddd()
    }
}
