def call(Map args){
    node(){
        stage('Checkout') {
                    // Использование параметра GIT_BRANCH для клонирования репозитория
                    checkout([$class: 'GitSCM',
                              branches: [[name: "${params.GIT_BRANCH}"]],
                              doGenerateSubmoduleConfigurations: false,
                              extensions: [],
                              submoduleCfg: [],
                              userRemoteConfigs: [[
                                  url: 'ssh://git@github.com/Zelgladis/JenkinsTest.git',
                                  credentialsId: "MY_GIT"
                              ]]
                    ])
        }
        stage('RunPipeX'){
            args.each { arg ->
                echo "$arg"
            }
            params.each { para ->
                echo "$para"
            }
        }
    }
    
}