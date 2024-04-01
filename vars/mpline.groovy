// vars/mpline.groovy

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
        steps {
            echo 'This is the first stage'
        }
    }
}

def mySecondStage() {
    stage('Second Stage') {
        steps {
            echo 'This is the second stage'
        }
    }
}