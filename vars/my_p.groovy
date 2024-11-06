def addOrUpdateChoice() {
    // Создаём новый параметр выбора
    def newChoiceParam = [
        $class: 'ChoiceParameterDefinition',
        name: 'VersionRollback',
        choices: call(),
        description: 'Select a version to rollback'
    ]

    // Получаем текущие параметры, сохраняя их структуру
    def currentParams = []
    def found = false
    for (param in currentBuild.build().getProject().getProperty('parameters')) {
        if (param.name == 'VersionRollback') {
            currentParams << newChoiceParam // Заменяем существующий параметр
            found = true
        } else {
            currentParams << param // Добавляем все остальные параметры без изменений
        }
    }

    // Если параметр VersionRollback не найден, добавляем его в конец
    if (!found) {
        currentParams << newChoiceParam
    }

    // Устанавливаем параметры джобы с обновленным списком
    properties([
        parameters(currentParams)
    ])
}