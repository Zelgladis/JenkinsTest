def pipe1(){
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