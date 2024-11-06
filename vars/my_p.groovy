def adasafaffwfqffqwfwf() {
    // Список для хранения параметров
    def updatedParams = []

    // Проверка наличия параметра VersionRollback
    def rollbackExists = false

    // Собираем все текущие параметры
    for (param in params.keySet()) {
        def value = params[param]
        if (value instanceof Boolean) {
            updatedParams << [$class: 'BooleanParameterDefinition', name: param, defaultValue: value, description: "Existing boolean parameter: ${param}"]
        } else if (value instanceof String) {
            updatedParams << [$class: 'StringParameterDefinition', name: param, defaultValue: value, description: "Existing string parameter: ${param}"]
        } else {
            // Если есть другие типы, можно добавить доп. обработку
        }
    }

    // Если параметр VersionRollback не существует, добавляем его
    updatedParams << [
        $class: 'ChoiceParameterDefinition',
        name: 'VersionRollback',
        choices: timeline(),
        description: 'Select a version to rollback'
    ]

    // Устанавливаем параметры с новым и существующими значениями
    properties([
        parameters(updatedParams)
    ])
}

def addOrUpdateChoice() {
    // Создаем список параметров с сохранением порядка
    def orderedParams = []
    def rollbackUpdated = false

    // Определяем новый параметр выбора VersionRollback
    def newChoiceParam = [
        $class: 'ChoiceParameterDefinition',
        name: 'VersionRollback',
        choices: timeline(),
        description: 'Select a version to rollback'
    ]

    // Проходим по всем текущим параметрам, сохраняя их структуру
    for (param in params.keySet()) {
        if (param == 'VersionRollback') {
            // Обновляем VersionRollback, если он уже существует
            orderedParams << newChoiceParam
            rollbackUpdated = true
        } else {
            // Определяем типы других параметров
            def value = params[param]
            if (value instanceof Boolean) {
                orderedParams << [$class: 'BooleanParameterDefinition', name: param, defaultValue: value, description: "Existing boolean parameter: ${param}"]
            } else if (value instanceof String) {
                orderedParams << [$class: 'StringParameterDefinition', name: param, defaultValue: value, description: "Existing string parameter: ${param}"]
            }
            // Можно добавить дополнительные типы, если нужно
        }
    }

    // Добавляем параметр VersionRollback в конец, если его не было
    if (!rollbackUpdated) {
        orderedParams << newChoiceParam
    }

    // Устанавливаем параметры, сохраняя их порядок
    properties([
        parameters(orderedParams)
    ])
}
