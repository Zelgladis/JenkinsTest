def call(){
    stage('Parametr Initialisation'){
        def my_params = []

        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest', valueBool: true, description: 'Hello world'))
        my_params.add(paramen_ret(type: 'bool',name: 'BoolTest2', description: '2 world'))

        properties([
            parameters(my_params)
        ])
    }
}




//allParams.add(booleanParam(name: "TestBool", defaultValue: false, description: "Test boolean parameter"))
//allParams.add(string(name: "TestString", defaultValue: "default value", description: "Test string parameter"))
//allParams.add([
//    $class: 'ChoiceParameterDefinition', 
//    name: 'DynamicChoice', 
//    choices: ["123", "456", "789"],  // Новые значения
//    description: 'Select a dynamic option'
//])