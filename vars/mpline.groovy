def myPipelineFunction() {
    pipeline {
        agent any
        stages {
            stage('Example') {
                steps {
                    echo 'Hello from myPipelineFunction!'
                }
            }
        }
    }
}