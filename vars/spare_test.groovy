def call(){
    stage('Olyaya'){
        withCredentials([usernamePassword(credentialsId: 'MY_SUPER_KEY', usernameVariable: 'UNAME', passwordVariable: 'UPWD')]) {
            some_cred_act()
        }
    }
}

def some_cred_act(){
    echo "$UNAME"
}