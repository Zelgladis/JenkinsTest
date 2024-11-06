def addOrUpdateChoice() {
    // Список для хранения параметров
    def updatedParams = []

    // Проверка наличия параметра VersionRollback
    def rollbackExists = false

    // Собираем все текущие параметры
    for (param in params.keySet()) {
        if (param == 'VersionRollback') {
            // Если параметр VersionRollback уже есть, обновляем его
            updatedParams << [
                $class: 'ChoiceParameterDefinition',
                name: 'VersionRollback',
                choices: timeline(),
                description: 'Select a version to rollback'
            ]
            rollbackExists = true
        } else {
            // Обрабатываем другие типы параметров
            def value = params[param]
            if (value instanceof Boolean) {
                updatedParams << [$class: 'BooleanParameterDefinition', name: param, defaultValue: value, description: "Existing boolean parameter: ${param}"]
            } else if (value instanceof String) {
                updatedParams << [$class: 'StringParameterDefinition', name: param, defaultValue: value, description: "Existing string parameter: ${param}"]
            } else {
                // Если есть другие типы, можно добавить доп. обработку
            }
        }
    }

    // Если параметр VersionRollback не существует, добавляем его
    if (!rollbackExists) {
        updatedParams << [
            $class: 'ChoiceParameterDefinition',
            name: 'VersionRollback',
            choices: timeline(),
            description: 'Select a version to rollback'
        ]
    }

    // Устанавливаем параметры с новым и существующими значениями
    properties([
        parameters(updatedParams)
    ])
}