def call(){
    stage('Olyaya'){
        withCredentials([usernamePassword(credentialsId: 'GIT_TOKEN', usernameVariable: 'UNAME', passwordVariable: 'UPWD')]) {
            some_cred_act()
        }
    }
}

def some_cred_act(){
    echo "$UNAME"
}