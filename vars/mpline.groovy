def myPipelineFunction() {
    pipeline {
        agent any
        stages {
            stage('Hello World') {
                steps {
                    echo 'Hello from myPipelineFunction!'
                }
            }
        }
    }
}