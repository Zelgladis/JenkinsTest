def call1() {
    def pipelineClosure = myPipeline()
    pipelineClosure()
}

def call() {
    pipeline {
        agent any
        
        parameters {
            string(name: 'VERSION', defaultValue: '1.0', description: 'Version of the application')
            booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Deploy the application after build')
            booleanParam(name: 'TEST_IT', defaultValue: true, description: 'Deploy the application after build')
        }
        
        stages {
            stage('Build') {
                steps {
                    echo "Building version ${params.VERSION}"
                }
            }
            stage('Deploy') {
                when {
                    expression { params.DEPLOY == true }
                }
                steps {
                    echo "Deploying version ${params.VERSION}"
                    // Add deployment steps here
                }
            }
        }
    }
}

def myPipeline() {
    return {
        pipeline {
            agent any
            
            parameters {
                string(name: 'VERSION', defaultValue: '1.0', description: 'Version of the application')
                booleanParam(name: 'DEPLOY', defaultValue: true, description: 'Deploy the application after build')
            }
            
            stages {
                stage('Build') {
                    steps {
                        echo "Building version ${params.VERSION}"
                    }
                }
                stage('Deploy') {
                    when {
                        expression { params.DEPLOY == true }
                    }
                    steps {
                        echo "Deploying version ${params.VERSION}"
                        // Add deployment steps here
                    }
                }
            }
        }
    }
}