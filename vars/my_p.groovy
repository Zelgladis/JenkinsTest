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
    // Список для хранения параметров в исходном порядке
    def orderedParams = []
    def rollbackUpdated = false

    // Обходим текущие параметры и сохраняем их типы и значения
    for (param in params.keySet()) {
        if (param == 'VersionRollback') {
            // Обновляем существующий параметр VersionRollback
            orderedParams << [
                $class: 'ChoiceParameterDefinition',
                name: 'VersionRollback',
                choices: timeline(),
                description: 'Select a version to rollback'
            ]
            rollbackUpdated = true
        } else {
            def value = params[param]
            if (value instanceof Boolean) {
                orderedParams << [$class: 'BooleanParameterDefinition', name: param, defaultValue: value, description: "Existing boolean parameter: ${param}"]
            } else if (value instanceof String) {
                orderedParams << [$class: 'StringParameterDefinition', name: param, defaultValue: value, description: "Existing string parameter: ${param}"]
            }
            // Добавьте обработку других типов при необходимости
        }
    }

    // Если параметр VersionRollback не найден, добавляем его в конец
    if (!rollbackUpdated) {
        orderedParams << [
            $class: 'ChoiceParameterDefinition',
            name: 'VersionRollback',
            choices: timeline(),
            description: 'Select a version to rollback'
        ]
    }

    // Устанавливаем параметры, сохраняя исходный порядок
    properties([
        parameters(orderedParams)
    ])
}