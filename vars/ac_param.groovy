def call(){
    node {
        if (!params.DynamicChoice) {
            properties([
                parameters([
                    [$class: 'ChoiceParameterDefinition', 
                        name: 'DynamicChoice', 
                        choices: timeline(), 
                        description: 'Select a dynamic option']
                ])
            ])
        }

        
        stage("Example Stage") {
                echo "123Selected DynamicChoice: ${params.DynamicChoice}"
            }
            timeline()
        }
}

def addchoice(){
    call()
}

def add_to_param(){
    node {
        stage("Pohui"){
            params.DynamicChoice.choices = ["123"]
        }
    }
}