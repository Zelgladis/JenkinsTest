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

    properties([
        parameters(allParams)
    ])
}


def addChoice() {
    // Получаем текущие параметры
    def currentProperties = currentBuild.rawBuild.getAction(ParametersAction)?.parameters

    // Добавляем новый параметр
    def newParameter = new ChoiceParameterDefinition(
        'VersionRollback', 
        timeline(), 
        'Select a version to rollback'
    )
    
    // Проверяем, если параметр уже существует, то обновляем
    if (!currentProperties?.any { it.name == 'VersionRollback' }) {
        currentBuild.rawBuild.addAction(new ParametersAction(currentProperties + newParameter))
    }
}