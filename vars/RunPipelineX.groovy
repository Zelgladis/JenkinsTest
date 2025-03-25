def call(Map args){
    node(){
        mpline.init_env(args)
        stage('Checkout') {
                    // Использование параметра GIT_BRANCH для клонирования репозитория
                    //checkout([$class: 'GitSCM',
                    //          branches: [[name: "${params.GIT_BRANCH}"]],
                    //          doGenerateSubmoduleConfigurations: false,
                    //          extensions: [],
                    //          submoduleCfg: [],
                    //          userRemoteConfigs: [[
                    //              url: 'ssh://git@github.com/Zelgladis/JenkinsTest.git',
                    //              credentialsId: "MY_GIT"
                    //          ]]
                    //])
                    echo args.target_list.collect {"\n\t\t'${it}'" }.join(', ')
        }
        stage('RunPipeX'){
            args.each { arg ->
                echo "$arg"
            }
            params.each { para ->
                echo "$para"
            }
            args.target_list.each { trg ->
                echo "$trg"
            }
        }
    }
    
}
