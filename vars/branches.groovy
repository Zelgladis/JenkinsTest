def user_name_git()
{
    stage('Get Remote Git Branches') {
        // Выполняем команду git ls-remote --heads и сохраняем результат в переменную
        withCredentials([usernamePassword(credentialsId: 'GIT_TOKEN', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_TOKEN')]) {
            def remoteBranches = sh(script: 'git ls-remote --heads https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/Zelgladis/MangaStorage.git', returnStdout: true).trim()
        
            // Выводим список веток
            echo "Remote Branches: \n${remoteBranches}"
            
            // Преобразуем результат в список для дальнейшей обработки
            def branchList = remoteBranches.split('\n').collect { it.split()[1].replace('refs/heads/', '') }
            
            // Выводим список веток в цикле
            branchList.each { branch ->
                echo "Branch: ${branch}"
            }
        }
    }
}
    

def sshagent_git(){
    stage('Get Remote Git Branches') {
        // Выполняем команду git ls-remote --heads и сохраняем результат в переменную
        sshagent(credentials: ['MY_GIT']) {
                        def remoteBranches = sh(script: """
                            git ls-remote --heads git@github.com:Zelgladis/MangaStorage.git
                        """, returnStdout: true).trim()
        
            // Выводим список веток
            echo "Remote Branches: \n${remoteBranches}"
            
            // Преобразуем результат в список для дальнейшей обработки
            def branchList = remoteBranches.split('\n').collect { it.split()[1].replace('refs/heads/', '') }
            
            // Выводим список веток в цикле
            branchList.each { branch ->
                echo "Branch: ${branch}"
            }
        }
    }
}