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

def myPipelineFunction() {
    stage('Hello World') {
            steps {
                echo 'Hello from MyLibrary!'
            }
        }
}