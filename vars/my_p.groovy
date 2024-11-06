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


def addChoice1() {
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

def addChoice2() {
    // Получаем текущие параметры
    def existingParams = params.keySet().collect { key ->
        [$class: 'StringParameterDefinition', name: key, defaultValue: params[key], description: "Existing parameter: ${key}"]
    }

    // Добавляем новый параметр, сохраняя старые
    def newParam = [
        $class: 'ChoiceParameterDefinition',
        name: 'VersionRollback',
        choices: timeline(),
        description: 'Select a version to rollback'
    ]

    // Обновляем параметры в джобе
    properties([
        parameters(existingParams + newParam)
    ])

    echo params
}


def addOrUpdateChoice() {
    // Получаем текущие параметры и определяем их типы
    def existingParams = params.keySet().collect { key ->
        def value = params[key]
        
        switch (value.class) {
            case Boolean:
                return [$class: 'BooleanParameterDefinition', name: key, defaultValue: value, description: "Existing boolean parameter: ${key}"]
            case String:
                return [$class: 'StringParameterDefinition', name: key, defaultValue: value, description: "Existing string parameter: ${key}"]
            default:
                return null // Для других типов можно добавить обработку
        }
    }.findAll { it != null }

    // Проверяем наличие и обновляем или добавляем Choice параметр
    def choiceParamName = 'VersionRollback'
    def choiceParam = existingParams.find { it.name == choiceParamName }
    if (choiceParam) {
        // Если параметр уже существует, обновляем его
        choiceParam.choices = timeline()  // Обновляем значения выборов
    } else {
        // Если параметр не существует, добавляем его
        existingParams << [
            $class: 'ChoiceParameterDefinition',
            name: choiceParamName,
            choices: timeline(),
            description: 'Select a version to rollback'
        ]
    }

    // Устанавливаем свойства, включая все существующие параметры
    properties([
        parameters(existingParams)
    ])
}