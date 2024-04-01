// vars/mpline.groovy
package mpline

def call() {
    echo 'Worked!'
}

def myPipelineFunction2() {
    pipeline {
        agent { label 'any' }
        stages {
            stage('Hello World') {
                steps {
                    echo 'Hello from myPipelineFunction!'
                }
            }
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