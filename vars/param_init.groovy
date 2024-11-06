def call(){
    stage('Parametr Initialisation'){
        def my_params = []

        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest', valueBool: true, description: 'Hello world'))
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest2', description: '2 world'))

        my_params = my_params.findAll { it != null }

        properties([
            parameters(my_params)
        ])
    }
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
        default:
            ret = "Ни одно из условий не подошло"
    }
    return ret
}




//allParams.add(booleanParam(name: "TestBool", defaultValue: false, description: "Test boolean parameter"))
//allParams.add(string(name: "TestString", defaultValue: "default value", description: "Test string parameter"))
//allParams.add([
//    $class: 'ChoiceParameterDefinition', 
//    name: 'DynamicChoice', 
//    choices: ["123", "456", "789"],  // Новые значения
//    description: 'Select a dynamic option'
//])