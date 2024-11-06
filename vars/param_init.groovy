def call(){
    stage('Parametr Initialisation'){
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest', valueBool: true, description: 'Hello world'))
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest2', description: '2 world'))
        reInit()
    }
}

def reInit(){
    my_params = my_params.findAll { it != null }
    properties([
            parameters(my_params)
        ])
}

def paramen_ret(Map args){
    def ret
    pname = args.get('name', 'Null')
    choices = args.get('choices', 'Null')
    value = args.get('value', 'Null')
    valueBool = args.get('valueBool', false)
    type = args.get('type', 'Null')
    defValue = args.get('defValue', 'Null')
    parclass = args.get('parclass', 'Null')
    description = args.get('description', 'Null')

    switch (type) {
        case 'bool':
            ret = booleanParam(name: pname, defaultValue: valueBool, description: description)
            break
        case 'string':
            ret = string(name: pname, defaultValue: value, description: description)
            break
        case 'choice':
            ret = [
                $class: 'ChoiceParameterDefinition', 
                name: pname, 
                choices: choices,
                description: description
            ]
            break
        default:
            ret = "Ни одно из условий не подошло"
    }
    return ret
}

