def call(){
    node {
        properties([
            parameters([
                [$class: 'ChoiceParameterDefinition', 
                    name: 'DynamicChoice2', 
                    choices: timeline(), 
                    description: 'Select a dynamic option']
            ])
        ])

        
        stage("Example Stage") {
                echo "123Selected DynamicChoice: ${params.DynamicChoice}"
            }
            timeline()
        }
}

def addchoice(){
    call()
}
