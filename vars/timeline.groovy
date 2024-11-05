def call(){
    stage("Clean"){
        cleanWs()
    }
    stage("ParseTheWOrld"){
        withCredentials([usernamePassword(credentialsId: 'git_key', 
                                                        usernameVariable: 'GIT_USERNAME', 
                                                        passwordVariable: 'GIT_PASSWORD')]) {
            sh"""
                git clone https://\$GIT_USERNAME:\$GIT_PASSWORD@github.com/Zelgladis/JenkinsTest.git
                ls
            """
            def versions = []
            def command = "cat testUsers.json"
            def process = command.execute()
            process.in.eachLine { line ->
                def history = new groovy.json.JsonSlurper().parseText(line)
                versions = history.collect { it.revision }
            }
            echo versions
        }
    }
}