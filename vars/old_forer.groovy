pipeline {
    agent any
    stages {
        stage('Push to Target Repository') {
            steps {
                    script{
                        def target_list = ['repo1', 'repo2']
                        for (int i = 0; i < target_list.size(); i++) {
                            withEnv(["iplus=${i+1}", "trepo=${target_list[i]}"]) {
                                sh '''
                                echo target-repo${iplus}
                                echo "${trepo}"
                                '''
                            }
                        }
                    }
                }
            }
        }
}

