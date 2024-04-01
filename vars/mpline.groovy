// vars/mpline.groovy

def call() {
    echo 'Worked!'
}

def step1() {
    node () {
        stage ("a stage inside node"){
            // do stuff here
            echo 'This is the first stage'
            def m_class = new MyClass('Hello World')
            env.cringe = m_class.yami
        }
    }
}


def step2(String x) {
    node () {
        stage ("a stage inside node"){
            // do stuff here
            when {
                x 'true'
            }
            echo 'This is the first stage'
            def m_class = new MyClass('Hello World')
            env.cringe = m_class.yami
        }
    }
}

def step3() {
    node () {
        stage ("a stage inside node2"){
            // do stuff here
            echo 'This is the first stage'
            echo "${env.cringe}"
            sh """
            echo "$cringe"
            """
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