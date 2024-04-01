// vars/mpline.groovy
package mpline

def call() {
    echo 'Worked!'
}

def step1() {
    node () {
        stage ("a stage inside node"){
            // do stuff here
            echo 'This is the first stage'
        }
    }
}

def step2() {
    node () {
        stage ("a stage inside node2"){
            // do stuff here
            echo 'This is the first stage'
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