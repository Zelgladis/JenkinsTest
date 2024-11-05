def call(){
    stage("Clean"){
        cleanWs()
    }
    stage("ParseTheWOrld"){
        withCredentials([usernamePassword(credentialsId: 'git_key', 
                                                        usernameVariable: 'GIT_USERNAME', 
                                                        passwordVariable: 'GIT_PASSWORD')]) {
            sh"""
                git clone https://\$GIT_USERNAME:\$GIT_PASSWORD@github.com/Zelgladis/JenkisTestGit.git
                ls ./JenkinsTest/
            """
            // Тут твоя команда с helm -o json
            def jsonContent = sh(script: 'cat ./JenkisTestGit/testUsers.json', returnStdout: true).trim()
            // Парсим JSON
            def history = new groovy.json.JsonSlurper().parseText(jsonContent)
            // Извлекаем версии
            def versions = history.collect { it.revision }
            // Выводим версии
            echo "Versions: ${versions.join(', ')}"
        }
    }   
}