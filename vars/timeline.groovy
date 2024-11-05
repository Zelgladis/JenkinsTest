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
        }
    }
}