// vars/mpline.groovy

def call() {
    echo 'Worked!'
}

def init_env() {
    node () {
        stage ("Init ENV"){
            // do stuff here
            def m_class = new MyClass('Hello World')
            env.cringe = m_class.yami
        }
    }
}

def git_pull() {
    node(){
        stage('🥵Git pull project') {
            info = git (
                    branch: 'main',
                    credentialsId: 'GIT_TOKEN',
                    url: 'https://github.com/Zelgladis/JenkisTestGit.git'
            )
            sh 'ls'
            
        }
    }
}

def step1(String x) {
    node () {
        if (x == '1') {
            stage ("Good stage 1"){
            echo 'This is the first stage'
            def m_class = new MyClass('Hello World')
            env.cringe = m_class.yami
        }
        } else {
            echo 'Skipping...'
        }
    }
}

def step_print() {
    node () {
        stage ("step_print"){
            // do stuff here
            echo 'This is the first stage'
            echo "${env.cringe}"
            sh """
            echo "$cringe"
            """
        }
    }
}

def failed_step(){
    when {
        expression {
            false == true
        }
    }
    steps {
        echo 'Tests skipped'
    }
}

def clean(){
    node () {
        stage ("Cleaning..."){
            // do stuff here
            echo 'Clean complete!'
        }
    }
}

def myFirstStage() {
    stage('First Stage') {
        echo 'This is the first stage'
    }
}

def mySecondStage(String envVar) {
    stage('Second Stage') {
        echo "This is the second stage $envVar"
    }
}


def myNewFirstStage() {
    stage('New First Stage') {
        echo 'This is my new first stage'
    }
}