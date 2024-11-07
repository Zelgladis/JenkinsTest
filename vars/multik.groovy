def call(){
    //mpline.git_pull('main', 'https://github.com/Zelgladis/JenkinsTest.git')
    def tes = params.isEmpty()
    echo "$tes"
    if (params.isEmpty() == false) {
        echo "Set params to []"
        setParams([])
    }else{
        echo "Set params to FULL"
        def my_params = []
        my_params.add(booleanParam(name: 'Booler', defaultValue: false, description: 'Booler desc'))
        my_params.add(string(name: 'Stringer', defaultValue: 'empty', description: 'Stringer desc'))
        my_params.add([
                $class: 'ChoiceParameterDefinition', 
                name: 'ChoiceMe', 
                choices: ['1', '2', '3'],
                description: 'some choice desc'
            ])
        setParams(my_params)
    }
}

def setParams(List my_params){
    my_params = my_params.findAll { it != null }
    properties([
            parameters(my_params)
        ])
}