def call(){
    def job = Jenkins.instance.getItemByFullName(env.JOB_NAME)
    def paramsProperty = job.getProperty(ParametersDefinitionProperty)
    
    def existingParams = paramsProperty.parameterDefinitions.collect { param ->
        switch (param.class) {
            case ChoiceParameterDefinition:
                if (param.name == 'VersionRollback') {
                    // Обновляем выборы, если параметр уже существует
                    return new ChoiceParameterDefinition('VersionRollback', timeline(), 'Select a version to rollback')
                } else {
                    return param // Сохраняем текущий Choice параметр
                }
            case BooleanParameterDefinition:
                return new BooleanParameterDefinition(param.name, param.defaultValue, param.description)
            case StringParameterDefinition:
                return new StringParameterDefinition(param.name, param.defaultValue, param.description)
            default:
                return param // Другие типы параметров добавляются без изменений
        }
    }

    // Проверяем, был ли параметр 'VersionRollback' найден и добавляем его, если нет
    if (!existingParams.any { it.name == 'VersionRollback' }) {
        existingParams << new ChoiceParameterDefinition('VersionRollback', timeline(), 'Select a version to rollback')
    }

    // Применяем обновлённые параметры к джобе
    properties([
        parameters(existingParams)
    ])
}