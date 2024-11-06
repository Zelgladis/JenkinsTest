def call(){
    def allParams = []
    allParams.add(booleanParam(name: "TestBool", defaultValue: false, description: "Test boolean parameter"))
    allParams.add(string(name: "TestString", defaultValue: "default value", description: "Test string parameter"))

    // Обновляем DynamicChoice с новыми значениями
    allParams.add([
        $class: 'ChoiceParameterDefinition', 
        name: 'DynamicChoice', 
        choices: ["123", "456", "789"],  // Новые значения
        description: 'Select a dynamic option'
    ])
}